package it.jiniux.gdlp.core.application;

import java.util.List;
import java.util.Optional;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.BookFilterDto;
import it.jiniux.gdlp.core.application.mappers.BookFilterMapper;
import it.jiniux.gdlp.core.application.mappers.BookMapper;
import it.jiniux.gdlp.core.domain.*;
import it.jiniux.gdlp.core.domain.exceptions.BookDoesNotExistException;
import it.jiniux.gdlp.core.domain.exceptions.DomainException;
import it.jiniux.gdlp.core.domain.filters.Filter;

public class BookService {
    private final DataAccessProvider dataAccessProvider;
    private final EventBus eventBus;

    public BookService(DataAccessProvider dataAccessProvider, EventBus eventBus) {
        this.dataAccessProvider = dataAccessProvider;
        this.eventBus = eventBus;
    }

    public void createBook(BookDto bookDto) throws DomainException {
        BookRepository bookRepository = dataAccessProvider.getBookRepository();
        TransactionManager transactionManager = dataAccessProvider.getTransactionManager();

        Book book = BookMapper.getInstance().toDomain(bookDto);

        transactionManager.execute(() -> {
            BookCreationPolicy bookCreationPolicy = new BookCreationPolicy(bookRepository);
            bookCreationPolicy.validate(book);

            bookRepository.saveBook(book);
        });

        BookDto createdBookDto = BookMapper.getInstance().toDto(book);
        eventBus.publish(new Event.BookCreated(createdBookDto));
    }

    public void editBook(BookDto bookDto) throws DomainException {
        BookRepository bookRepository = dataAccessProvider.getBookRepository();
        TransactionManager transactionManager = dataAccessProvider.getTransactionManager();

        Book.Id bookId = new Book.Id(bookDto.getId());

        transactionManager.execute(() -> {
            boolean bookExists = bookRepository.existsBookById(bookId);

            // If no editions are provided and the book exists, delete it
            if (bookDto.getEditions().isEmpty() && bookExists) {
                deleteBook(bookDto.getId());
                return;
            }

            if (!bookExists) {
                throw new BookDoesNotExistException(bookId);
            }

            Book editedBook = BookMapper.getInstance().toDomain(bookDto);

            BookEditPolicy bookEditPolicy = new BookEditPolicy(bookRepository);
            bookEditPolicy.validate(editedBook);

            bookRepository.saveBook(editedBook);
        });

        eventBus.publish(new Event.BookUpdated(bookDto));
    }

    public void deleteBook(String id) throws DomainException {
        Book.Id bookId = new Book.Id(id);
        BookRepository bookRepository = dataAccessProvider.getBookRepository();
        TransactionManager transactionManager = dataAccessProvider.getTransactionManager();

        transactionManager.execute(() -> {
            Optional<Book> bookOptional = bookRepository.findBookById(bookId);

            if (bookOptional.isEmpty()) {
                throw new BookDoesNotExistException(bookId);
            }

            Book book = bookOptional.get();
            bookRepository.deleteBook(book);
        });

        eventBus.publish(new Event.BookDeleted(id));
    }

    public List<BookDto> findBooks(BookFilterDto filter) {
        BookRepository bookRepository = dataAccessProvider.getBookRepository();
        // No need for transaction manager here as we are only reading data one time

        Filter<Book> bookFilter = BookFilterMapper.getInstance().toDomain(filter);

        List<Book> books = bookRepository.filterBooks(bookFilter);


        return books.stream()
                .map(BookMapper.getInstance()::toDto)
                .toList();
    }

    public Optional<BookDto> findBookByIsbn(String isbn) throws DomainException {
        BookRepository bookRepository = dataAccessProvider.getBookRepository();

        return bookRepository.findBookByIsbn(new Isbn(isbn))
                .map(BookMapper.getInstance()::toDto);
    }
}
