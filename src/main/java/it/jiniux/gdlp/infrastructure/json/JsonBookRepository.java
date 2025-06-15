package it.jiniux.gdlp.infrastructure.json;

import it.jiniux.gdlp.core.domain.*;
import it.jiniux.gdlp.core.domain.filters.Filter;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryBookRepository;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryTransactionManager;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public class JsonBookRepository implements BookRepository {
    private final InMemoryBookRepository inMemoryBookRepository;
    private final JsonTransactionManager transactionManager;
    private final JsonFile jsonFile;

    public JsonBookRepository(InMemoryBookRepository inMemoryBookRepository, JsonTransactionManager jsonTransactionManager, JsonFile jsonFile) {
        this.inMemoryBookRepository = inMemoryBookRepository;
        this.jsonFile = jsonFile;
        this.transactionManager = jsonTransactionManager;

        this.inMemoryBookRepository.resetBooks(this.jsonFile.load());
    }

    @Override
    public void saveBook(Book book) {
        transactionManager.acquireReentrantLock(false);
        try {
            inMemoryBookRepository.saveBook(book);
            this.jsonFile.update(inMemoryBookRepository.getBooks());
        } finally {
            transactionManager.releaseReentrantLock(false);
        }
    }

    @Override
    public void deleteBook(Book book) {
        transactionManager.acquireReentrantLock(false);
        try {
            inMemoryBookRepository.deleteBook(book);
            this.jsonFile.update(inMemoryBookRepository.getBooks());
        } finally {
            transactionManager.releaseReentrantLock(false);
        }
        inMemoryBookRepository.deleteBook(book);
    }

    @Override
    public Optional<Book> findBookByIsbn(Isbn isbn) {
        return inMemoryBookRepository.findBookByIsbn(isbn);
    }

    @Override
    public Optional<Book> findBookById(Book.Id id) {
        return inMemoryBookRepository.findBookById(id);
    }

    @Override
    public boolean existsBookById(Book.Id id) {
        return inMemoryBookRepository.existsBookById(id);
    }

    @Override
    public BookSearchResult findBooks(Filter<Book> filter, int page, int limit, SortBy sortBy) {
        return inMemoryBookRepository.findBooks(filter, page, limit, sortBy);
    }

    @Override
    public Set<Isbn> findAlreadyExistingIsbns(List<Isbn> isbns) {
        return inMemoryBookRepository.findAlreadyExistingIsbns(isbns);
    }

    @Override
    public Optional<Book> findBookByTitleAndAuthors(Book.Title title, Set<Author> authors) {
        return inMemoryBookRepository.findBookByTitleAndAuthors(title, authors);
    }

    @Override
    public Set<Author> findAllAuthorsContaining(String query, int limit) {
        return inMemoryBookRepository.findAllAuthorsContaining(query, limit);
    }

    @Override
    public Set<Genre> findAllGenresContaining(String query, int limit) {
        return inMemoryBookRepository.findAllGenresContaining(query, limit);
    }

    @Override
    public Set<Publisher> findAllPublishersContaining(String query, int limit) {
        return inMemoryBookRepository.findAllPublishersContaining(query, limit);
    }

    @Override
    public Set<Edition.Language> findAllLanguagesContaining(String query, int limit) {
        return inMemoryBookRepository.findAllLanguagesContaining(query, limit);
    }

    public void close() {
        inMemoryBookRepository.close();
        jsonFile.update(inMemoryBookRepository.getBooks());
        try {
            jsonFile.close();
        } catch (Exception e) {
            throw new RuntimeException("Failed to close JSON file", e);
        }
    }
}
