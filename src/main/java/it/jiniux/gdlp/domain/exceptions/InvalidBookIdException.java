package it.jiniux.gdlp.domain.exceptions;

public class InvalidBookIdException extends DomainException {
    public InvalidBookIdException(String id) {
        super("Invalid book ID provided: " + id);
    }
}
