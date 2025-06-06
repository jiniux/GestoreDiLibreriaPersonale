package it.jiniux.gdlp.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import it.jiniux.gdlp.domain.Author;
import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.BookRepository;
import it.jiniux.gdlp.domain.Book.Title;
import it.jiniux.gdlp.domain.Isbn;
import it.jiniux.gdlp.domain.filters.Filter;

public class InMemoryBookRepository implements BookRepository {
    private final InMemoryTransactionManager transactionManager;

    public InMemoryBookRepository(InMemoryTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

    private final List<Book> books = new ArrayList<>();
    private final Map<Isbn, Book> isbnIndex = new HashMap<>();
    private final Map<Book.Id, Book> bookIdIndex = new HashMap<>();

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

        transactionManager.acquireLock(true);
        return true;
    }

    public boolean acquireWriteLockIfNotInTransaction() {
        if (transactionManager.getCurrentTransactionState().isPresent()) {
            return false; // cannot acquire lock if already in a transaction
        }

        transactionManager.acquireLock(false);
        return true;
    }

    @Override
    public void saveBook(Book book) {
        boolean acquiredLock = acquireWriteLockIfNotInTransaction();

        try {
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
                transactionManager.releaseLock(false);
            }
        }
    }

    @Override
    public void deleteBook(Book book) {
        boolean acquiredLock = acquireWriteLockIfNotInTransaction();

        try {
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
                transactionManager.releaseLock(false);
            }
        }
    }

    @Override
    public Set<Isbn> findAlreadyExistingIsbns(List<Isbn> isbns) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Set<Isbn> existingIsbns;
        try {
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
                transactionManager.releaseLock(true);
            }
        }

        return existingIsbns;
    }

    @Override
    public Optional<Book> findBookByTitleAndAuthors(Title title, Set<Author> authors) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Optional<Book> b;

        try {
            b = books.stream()
                    .filter(book -> book.getTitle().equals(title) && book.getAuthors().equals(authors))
                    .findFirst();
        } finally {
            if (acquiredLock) {
                transactionManager.releaseLock(true);
            }
        }

        return b;
    }

    @Override
    public Optional<Book> findBookByIsbn(Isbn isbn) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        Optional<Book> book;
        try {
            book = isbnIndex.containsKey(isbn) ?
                Optional.of(isbnIndex.get(isbn)) :
                books.stream().filter(b -> b.getIsbns().contains(isbn)).findFirst();
        } finally {
            if (acquiredLock) {
                transactionManager.releaseLock(true);
            }
        }
        
        return book;
    }

    @Override
    public Optional<Book> findBookById(Book.Id id) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();
        
        Optional<Book> book;
        try {
            book = Optional.ofNullable(bookIdIndex.get(id));
        } finally {
            if (acquiredLock) {
                transactionManager.releaseLock(true);
            }
        }
        
        return book;
    }

    @Override
    public boolean existsBookById(Book.Id id) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();
        
        boolean exists;
        try {
            exists = bookIdIndex.containsKey(id);
        } finally {
            if (acquiredLock) {
                transactionManager.releaseLock(true);
            }
        }
        
        return exists;
    }

    @Override
    public List<Book> filterBooks(Filter<Book> filter) {
        boolean acquiredLock = acquireReadLockIfNotInTransaction();

        List<Book> filteredBooks;
        try {
            filteredBooks = new ArrayList<>();
            for (Book book : books) {
                if (filter.apply(book)) {
                    filteredBooks.add(book);
                }
            }
        } finally {
            if (acquiredLock) {
                transactionManager.releaseLock(true);
            }
        }

        return filteredBooks;
    }
}
