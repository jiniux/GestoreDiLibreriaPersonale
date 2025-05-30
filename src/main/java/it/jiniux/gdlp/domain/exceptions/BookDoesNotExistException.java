package it.jiniux.gdlp.domain.exceptions;

import it.jiniux.gdlp.domain.Book;

public class BookDoesNotExistException extends DomainException {
    public BookDoesNotExistException(Book.Id bookId) {
        super("Book with ID " + bookId.getValue() + " does not exist.");
    }
}
