package it.jiniux.gdlp.domain.exceptions;

public class BookDescriptionEmptyException extends DomainException {
    public BookDescriptionEmptyException() {
        super("Description cannot be empty");
    }
}
