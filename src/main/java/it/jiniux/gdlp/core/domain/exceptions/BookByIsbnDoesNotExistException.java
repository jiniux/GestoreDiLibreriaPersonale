package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Isbn;

public class BookByIsbnDoesNotExistException extends DomainException {
    private final Isbn isbn;

    public BookByIsbnDoesNotExistException(Isbn isbn) {
        super("Book with ISBN " + isbn + " does not exist.");
        this.isbn = isbn;
    }

    public Isbn getIsbn() {
        return isbn;
    }
}
