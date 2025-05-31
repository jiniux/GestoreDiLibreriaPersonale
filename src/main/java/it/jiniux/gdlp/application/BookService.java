package it.jiniux.gdlp.application;

import java.util.Optional;

import it.jiniux.gdlp.application.dtos.BookDto;
import it.jiniux.gdlp.application.mappers.BookMapper;
import it.jiniux.gdlp.domain.*;
import it.jiniux.gdlp.domain.exceptions.BookDoesNotExistException;
import it.jiniux.gdlp.domain.exceptions.DomainException;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void createBook(BookDto bookDto) throws DomainException {
        Book book = BookMapper.getInstance().toDomain(bookDto);

        BookCreationPolicy bookCreationPolicy = new BookCreationPolicy(bookRepository);
        bookCreationPolicy.validate(book);

        bookRepository.saveBook(book);
    }

    public void editBook(BookDto bookDto) throws DomainException {
        Book editedBook = BookMapper.getInstance().toDomain(bookDto);

        BookEditPolicy bookEditPolicy = new BookEditPolicy(bookRepository);
        bookEditPolicy.validate(editedBook);

        bookRepository.patchBook(editedBook);
    }

    public void deleteBook(String id) throws DomainException {
        Book.Id bookId = new Book.Id(id);
        Optional<Book> bookOptional = bookRepository.findBookById(bookId);

        if (bookOptional.isEmpty()) {
            throw new BookDoesNotExistException(bookId);
        }

        Book book = bookOptional.get();
        bookRepository.deleteBook(book);
    }


    public Optional<BookDto> findBookByIsbn(String isbn) throws DomainException {
        return bookRepository.findBookByIsbn(new Isbn(isbn))
                .map(BookMapper.getInstance()::toDto);
    }
}
