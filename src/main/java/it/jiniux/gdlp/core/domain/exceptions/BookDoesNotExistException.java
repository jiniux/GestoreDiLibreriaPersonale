package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Book;

public class BookDoesNotExistException extends DomainException {
    public BookDoesNotExistException(Book.Id bookId) {
        super("Book with ID " + bookId.getValue() + " does not exist.");
    }
}
