package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Book;
import lombok.Getter;

@Getter
public class BookDoesNotExistException extends DomainException {
    private final String bookId;

    public BookDoesNotExistException(Book.Id bookId) {
        super("Book with ID " + bookId.getValue() + " does not exist.");
        this.bookId = bookId.toString();
    }
}
