package it.jiniux.gdlp.infrastructure.inmemory;

import java.util.*;
import java.util.stream.Stream;

import it.jiniux.gdlp.core.domain.*;
import it.jiniux.gdlp.core.domain.Book.Title;
import it.jiniux.gdlp.core.domain.filters.Filter;

public class InMemoryBookRepository implements BookRepository {
    private boolean closed;

    public void close() {
        if (closed) {
            throw new IllegalStateException("Repository already closed.");
        }
        closed = true;
    }

    public List<Book> getBooks() {
        return Collections.unmodifiableList(books);
    }

    private final InMemoryTransactionManager transactionManager;

    public InMemoryBookRepository(InMemoryTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    public void resetBooks(List<Book> books) {
        this.books.clear();
        this.isbnIndex.clear();
        this.bookIdIndex.clear();

        for (Book book : books) {
            this.books.add(book);
            for (Isbn isbn : book.getIsbns()) {
                this.isbnIndex.put(isbn, book);
            }
            this.bookIdIndex.put(book.getId(), book);
        }
    }

    private final List<Book> books = new ArrayList<>();
    private final Map<Isbn, Book> isbnIndex = new HashMap<>();
    private final Map<Book.Id, Book> bookIdIndex = new HashMap<>();

    public void ensureNotClosed() {
        if (closed) {
            throw new IllegalStateException("Repository is closed.");
        }
    }

    private void addBookRemoveWhileSaveRollback(Book book) {
        transactionManager.getCurrentTransactionState().ifPresent(state -> {
            state.addRollbackAction(() -> {
                books.add(book);
            });
        });
    }

    private void addBookAddRollback(Book book) {
        transactionManager.getCurrentTransactionState().ifPresent(state -> {
            state.addRollbackAction(() -> {
                books.remove(book);
            });
        });
    }

    private void addIsbnAddRollback(Isbn isbn) {
        transactionManager.getCurrentTransactionState().ifPresent(state -> {
            state.addRollbackAction(() -> {
                isbnIndex.remove(isbn);
            });
        });
    }

    private void addIsbnRemoveRollback(Isbn isbn, Book book) {
        transactionManager.getCurrentTransactionState().ifPresent(state -> {
            state.addRollbackAction(() -> {
                isbnIndex.put(isbn, book);
            });
        });
    }

    private void addBookIdAddRollback(Book.Id bookId) {
        transactionManager.getCurrentTransactionState().ifPresent(state -> {
            state.addRollbackAction(() -> {
                bookIdIndex.remove(bookId);
            });
        });
    }

    private void addBookIdRemoveRollback(Book.Id bookId, Book book) {
        transactionManager.getCurrentTransactionState().ifPresent(state -> {
            state.addRollbackAction(() -> {
                bookIdIndex.put(bookId, book);
            });
        });
    }

    public boolean acquireReadLockIfNotInTransaction() {
        if (transactionManager.getCurrentTransactionState().isPresent()) {
            return false; // cannot acquire lock if already in a transaction
        }

        transactionManager.acquireReentrantLock(true);
        return true;
    }

    public boolean acquireWriteLockIfNotInTransaction() {
        if (transactionManager.getCurrentTransactionState().isPresent()) {
            return false; // cannot acquire lock if already in a transaction
        }

        transactionManager.acquireReentrantLock(false);
        return true;
    }

    @Override
    public void saveBook(Book book) {
        boolean acquiredLock = acquireWriteLockIfNotInTransaction();

        try {
            ensureNotClosed();

            Optional<Book> existingBook = findBookById(book.getId());

            existingBook.ifPresent(b -> {
                books.remove(b);
                addBookRemoveWhileSaveRollback(b);
            });

            boolean bookExisted = existingBook.isPresent();

            books.add(book);
            addBookAddRollback(book);

            for (Isbn isbn : book.getIsbns()) {
                Book alreadyExistingBook = isbnIndex.put(isbn, book);

                if (alreadyExistingBook != null && (!alreadyExistingBook.equals(book) || !bookExisted)) {
                    throw new IllegalStateException("ISBN " + isbn + " already exists for another book.");
                }

                addIsbnAddRollback(isbn);
            }

            Book previousBook = bookIdIndex.get(book.getId());
            if (previousBook != null && !previousBook.equals(book)) {
                throw new IllegalStateException("Book ID " + book.getId() + " already exists for another book.");
            }

            bookIdIndex.put(book.getId(), book);
            addBookIdAddRollback(book.getId());
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(false);
            }
        }
    }

    @Override
    public void deleteBook(Book book) {
        boolean acquiredLock = acquireWriteLockIfNotInTransaction();

        try {
            ensureNotClosed();

            boolean removed = books.removeIf(b -> b.getId().equals(book.getId()));
            if (!removed) {
                throw new IllegalArgumentException("Book with ID " + book.getId() + " does not exist.");
            }

            for (Isbn isbn : book.getIsbns()) {
                Book previousBook = isbnIndex.remove(isbn);

                if (previousBook == null || !previousBook.equals(book)) {
                    throw new IllegalStateException("ISBN " + isbn + " does not exist for the book being deleted.");
                }

                addIsbnRemoveRollback(isbn, previousBook);
            }

            Book previousBook = bookIdIndex.get(book.getId());

            if (previousBook == null || !previousBook.equals(book)) {
                throw new IllegalStateException("Book ID " + book.getId() + " does not exist for the book being deleted.");
            }

            bookIdIndex.remove(book.getId());

            addBookIdRemoveRollback(book.getId(), previousBook);
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(false);
            }
        }
    }

    @Override
    public Set<Isbn> findAlreadyExistingIsbns(List<Isbn> isbns) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Set<Isbn> existingIsbns;
        try {
            ensureNotClosed();

            existingIsbns = new HashSet<>();

            for (Book book : books) {
                Set<Isbn> bookIsbns = book.getIsbns();
                for (Isbn isbn : isbns) {
                    if (bookIsbns.contains(isbn)) {
                        existingIsbns.add(isbn);
                    }
                }
            }
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }

        return existingIsbns;
    }

    @Override
    public Optional<Book> findBookByTitleAndAuthors(Title title, Set<Author> authors) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Optional<Book> b;

        try {
            ensureNotClosed();

            b = books.stream()
                    .filter(book -> book.getTitle().equals(title) && book.getAuthors().equals(authors))
                    .findFirst();
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }

        return b;
    }

    @Override
    public Set<Author> findAllAuthorsContaining(String query, int limit) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Set<Author> authors;
        try {
            ensureNotClosed();

            authors = new HashSet<>();
            for (Book book : books) {
                for (Author author : book.getAuthors()) {
                    if (author.getName().getValue().contains(query)) {
                        authors.add(author);
                    }
                }
                if (authors.size() >= limit) {
                    break;
                }
            }
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }

        return authors;
    }

    @Override
    public Set<Genre> findAllGenresContaining(String genre, int limit) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Set<Genre> genres;
        try {
            ensureNotClosed();

            genres = new HashSet<>();
            for (Book book : books) {
                for (Genre bookGenre : book.getGenres()) {
                    if (bookGenre.getName().getValue().contains(genre)) {
                        genres.add(bookGenre);
                    }
                }
                if (genres.size() >= limit) {
                    break;
                }
            }
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }

        return genres;
    }

    @Override
    public Set<Publisher> findAllPublishersContaining(String query, int limit) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Set<Publisher> publishers;
        try {
            ensureNotClosed();

            publishers = new HashSet<>();
            for (Book book : books) {
                for (Edition edition : book.getEditions()) {
                    if (edition.getPublisher().getName().getValue().contains(query)) {
                        publishers.add(edition.getPublisher());
                    }
                }
                if (publishers.size() >= limit) {
                    break;
                }
            }
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }

        return publishers;
    }

    @Override
    public Set<Edition.Language> findAllLanguagesContaining(String query, int limit) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Set<Edition.Language> languages;
        try {
            ensureNotClosed();

            languages = new HashSet<>();
            for (Book book : books) {
                for (Edition edition : book.getEditions()) {
                    if (edition.getLanguage().isPresent()) {
                        Edition.Language language = edition.getLanguage().get();

                        if (language.getValue().contains(query)) {
                            languages.add(language);
                        }
                    }
                }
                if (languages.size() >= limit) {
                    break;
                }
            }
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }

        return languages;
    }

    @Override
    public Optional<Book> findBookByIsbn(Isbn isbn) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Optional<Book> book;
        try {
            ensureNotClosed();

            book = isbnIndex.containsKey(isbn) ?
                Optional.of(isbnIndex.get(isbn)) :
                books.stream().filter(b -> b.getIsbns().contains(isbn)).findFirst();
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }

        return book;
    }

    @Override
    public Optional<Book> findBookById(Book.Id id) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Optional<Book> book;
        try {
            ensureNotClosed();
            book = Optional.ofNullable(bookIdIndex.get(id));
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }
        
        return book;
    }

    @Override
    public boolean existsBookById(Book.Id id) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();
        ensureNotClosed();

        boolean exists;
        try {
            exists = bookIdIndex.containsKey(id);
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }
        
        return exists;
    }

    private Stream<Book> createSortedByTitleStream(Filter<Book> filter) {
        return books.stream().filter(filter::apply).sorted(Comparator.comparing(book -> book.getTitle().getValue()));
    }

    private Stream<Book> createSortedByPublicationYearStream(Filter<Book> filter) {
        return books.stream().filter(filter::apply).filter(book -> book.getEditions().stream().anyMatch(g -> g.getPublicationYear().isPresent()))
                .sorted(Comparator.comparing(book -> book.getEditions()
                        .stream()
                        .map(Edition::getPublicationYear)
                        .filter(Optional::isPresent)
                        .mapToInt(Optional::get)
                        .min()
                        .orElse(Integer.MAX_VALUE)));
    }

    @Override
    public BookSearchResult findBooks(Filter<Book> filter, int page, int limit, SortBy sortBy) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        long totalCount = 0;
        List<Book> filteredBooks;
        try {
            ensureNotClosed();
            int start = page * limit;

            Stream<Book> stream = Stream.of();

            if (sortBy == SortBy.TITLE) {
                totalCount = createSortedByTitleStream(filter).count();
                stream = createSortedByTitleStream(filter);
            } else if (sortBy == SortBy.PUBLICATION_YEAR) {
                totalCount = createSortedByPublicationYearStream(filter).count();
                stream = createSortedByPublicationYearStream(filter);
            }

            filteredBooks = stream
                    .skip(start)
                    .limit(limit)
                    .toList();
        } finally {
            if (acquiredLock) {
                transactionManager.releaseReentrantLock(true);
            }
        }

        return new BookSearchResult(filteredBooks, totalCount);
    }
}
