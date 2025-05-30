package it.jiniux.gdlp.domain.exceptions;

public class IsbnNullException extends DomainException {
    public IsbnNullException() {
        super("ISBN cannot be null");
    }
}
