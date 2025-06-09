package it.jiniux.gdlp.infrastructure.inmemory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import it.jiniux.gdlp.core.domain.*;
import it.jiniux.gdlp.core.domain.filters.EmptyFilter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import it.jiniux.gdlp.core.domain.exceptions.DomainException;

public class InMemoryBookRepositoryTests {
    private InMemoryDataAccessProvider dataAccessProvider;
    private InMemoryBookRepository bookRepository;
    private InMemoryTransactionManager transactionManager;
    
    @BeforeEach
    void setUp() {
        dataAccessProvider = new InMemoryDataAccessProvider();
        bookRepository = (InMemoryBookRepository) dataAccessProvider.getBookRepository();
        transactionManager = (InMemoryTransactionManager) dataAccessProvider.getTransactionManager();
    }
    
    private Book createTestBook(String title, String authorName, String isbnString, UUID id) throws DomainException {
        Book.Title bookTitle = new Book.Title(title);
        Author author = new Author(new Author.Name("author"));
        
        Isbn isbn = Isbn.createUnchecked(isbnString);
        Publisher publisher = new Publisher(new Publisher.Name("mondadori"));
        
        Edition edition = new Edition(isbn, publisher);
        
        Genre genre = new Genre(new Genre.Name("fiction"));
        
        Book.Builder builder = new Book.Builder(bookTitle)
            .addAuthor(author)
            .addEdition(edition)
            .addGenre(genre);
            
        if (id != null) {
            builder.id(new Book.Id(id));
        }
        
        return builder.build();
    }
    
    private Book createTestBook(String title, String authorName, String isbnString) throws DomainException {
        return createTestBook(title, authorName, isbnString, UUID.randomUUID());
    }
    
    @Test
    void shouldSaveAndRetrieveSuccessfully() throws DomainException {
        Book book = createTestBook("book1", "author1", "9788845292613");
        
        transactionManager.execute(() -> {
            bookRepository.saveBook(book);
        }, false);

        Optional<Book> retrievedBook = bookRepository.findBookById(book.getId());
        assertTrue(retrievedBook.isPresent());
        assertEquals(book.getId(), retrievedBook.get().getId());
        assertEquals(book.getTitle(), retrievedBook.get().getTitle());
    }
    
    @Test
    void shouldRollbackOnException() throws DomainException {
        Book book1 = createTestBook("book1", "author1", "9788845292613");
        
        transactionManager.execute(() -> {
            bookRepository.saveBook(book1);
        }, false);

        // attempt to save another book with the same isbn
        Book book2 = createTestBook("book2", "author2", "9788845292613");

        // should trigger rollback
        assertThrows(IllegalStateException.class, () -> {
            transactionManager.execute(() -> {
                bookRepository.saveBook(book2);
            }, false);
        });
        
        assertEquals(1, bookRepository.findBooks(new EmptyFilter<>(), 0, Integer.MAX_VALUE, BookRepository.SortBy.TITLE).getBooks().size());
        Optional<Book> foundBook = bookRepository.findBookById(book1.getId());
        assertTrue(foundBook.isPresent());
        assertEquals(book1.getId(), foundBook.get().getId());
    }
    
    @Test
    void shouldRollbackOnEditConflict() throws DomainException {
        Book book1 = createTestBook("book1", "author1", "9780441013593");
        Book book2 = createTestBook("book2", "author2", "9780132350884");
        
        transactionManager.execute(() -> {
            bookRepository.saveBook(book1);
            bookRepository.saveBook(book2);
        }, false);
        
        // try to update original book to include the ISBN already used by anotherBook
        UUID originalId = book1.getId().getValue();
        Book updatedBook = null;
        
        try {
            updatedBook = createTestBook("book1", "author1", "9780132350884", originalId);
        } catch (DomainException e) {
            fail("Should not throw exception during test setup: " + e.getMessage());
        }
        
        final Book testBook = updatedBook;
        
        // should trigger rollback
        assertThrows(IllegalStateException.class, () -> {
            transactionManager.execute(() -> {
                bookRepository.saveBook(testBook);
            }, false);
        });

        Optional<Book> retrievedOriginal = bookRepository.findBookById(book1.getId());
        assertTrue(retrievedOriginal.isPresent());
        assertEquals(book1.getId(), retrievedOriginal.get().getId());
        assertEquals(1, retrievedOriginal.get().getIsbns().size());
    }
    
    @Test
    void shouldRollbackOnDeleteBookConflict() throws DomainException {
        Book book = createTestBook("book1", "author1", "9780441013593");
        
        // Save the book
        transactionManager.execute(() -> {
            bookRepository.saveBook(book);
        }, false);
        
        // different book with the same id
        UUID bookId = book.getId().getValue();
        Book corruptedReference = createTestBook("Delete Test Book", "Delete Test Author", "9789999999999", bookId);
        
        // wrong isbn, should throw and rollback
        assertThrows(IllegalStateException.class, () -> {
            transactionManager.execute(() -> {
                bookRepository.deleteBook(corruptedReference);
            }, false);
        });
        
        assertTrue(bookRepository.existsBookById(book.getId()));
        Optional<Book> stillExists = bookRepository.findBookById(book.getId());
        assertTrue(stillExists.isPresent());
    }

    @Test
    void shouldNotDeadlockOnConcurrentReads() throws InterruptedException {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                books.add(createTestBook("Book " + i, "Author " + i, "978111111111" + i));
            } catch (DomainException e) {
                throw new RuntimeException(e);
            }
        }

        for (Book book : books) {
            bookRepository.saveBook(book);
        }

        int threadCount = 100;

        CountDownLatch readyLatch = new CountDownLatch(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        AtomicInteger successfulReads = new AtomicInteger(0);

        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            for (int i = 0; i < threadCount; i++) {
                final int index = i % books.size();
                executor.submit(() -> {
                    readyLatch.countDown();
                    try {
                        startLatch.await();
                        Optional<Book> book = bookRepository.findBookById(books.get(index).getId());
                        if (book.isPresent()) {
                            successfulReads.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        finishLatch.countDown();
                    }
                });
            }

            // wait all threads to be ready
            readyLatch.await();
            // signal all thread to start
            startLatch.countDown();

            // wait for all threads to finish
            assertTrue(finishLatch.await(5, TimeUnit.SECONDS));
        }

        // all reads should be successful
        assertEquals(threadCount, successfulReads.get());
    }

    @Test
    void shouldNotDeadlockOnConcurrentWrites() throws InterruptedException {
        List<Book> books = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            try {
                books.add(createTestBook("Book " + i, "Author " + i, "978111111111" + i));
            } catch (DomainException e) {
                throw new RuntimeException(e);
            }
        }

        int threadCount = 100;

        CountDownLatch readyLatch = new CountDownLatch(threadCount);
        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch finishLatch = new CountDownLatch(threadCount);
        AtomicInteger successfulWrites = new AtomicInteger(0);

        try (ExecutorService executor = Executors.newFixedThreadPool(threadCount)) {
            for (int i = 0; i < threadCount; i++) {
                final int index = i % books.size();
                executor.submit(() -> {
                    readyLatch.countDown();
                    try {
                        startLatch.await();
                        bookRepository.saveBook(books.get(index));
                        successfulWrites.incrementAndGet();
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    } finally {
                        finishLatch.countDown();
                    }
                });
            }

            // wait all threads to be ready
            readyLatch.await();
            // signal all thread to start
            startLatch.countDown();

            // wait for all threads to finish
            assertTrue(finishLatch.await(5, TimeUnit.SECONDS));
        }

        // all reads should be successful
        assertEquals(threadCount, successfulWrites.get());
    }

}
