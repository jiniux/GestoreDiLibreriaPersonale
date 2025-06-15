package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Isbn;
import lombok.Getter;

@Getter
public class BookByIsbnDoesNotExistException extends DomainException {
    private final String isbn;

    public BookByIsbnDoesNotExistException(Isbn isbn) {
        super("Book with ISBN " + isbn + " does not exist.");
        this.isbn = isbn.getValue();
    }
}
