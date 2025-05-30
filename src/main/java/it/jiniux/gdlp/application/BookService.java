package it.jiniux.gdlp.application;

import java.util.Optional;

import it.jiniux.gdlp.application.dtos.BookDto;
import it.jiniux.gdlp.application.mappers.BookMapper;
import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.BookRepository;
import it.jiniux.gdlp.domain.Isbn;
import it.jiniux.gdlp.domain.BookCreationPolicy;
import it.jiniux.gdlp.domain.exceptions.DomainException;

public class BookService {
    private final BookRepository bookRepository;

    public BookService(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    public void createBook(BookDto request) throws DomainException {
        Book book = BookMapper.getInstance().toDomain(request);

        BookCreationPolicy bookCreationPolicy = new BookCreationPolicy(bookRepository);
        bookCreationPolicy.validate(book);

        bookRepository.saveBook(book);
    }

    public Optional<BookDto> getBookById(String isbn) throws DomainException {
        return bookRepository.findBookByIsbn(new Isbn(isbn))
                .map(BookMapper.getInstance()::toDto);
    }
}
