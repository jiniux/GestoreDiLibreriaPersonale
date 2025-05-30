package it.jiniux.gdlp.domain.exceptions;

public class AuthorNameEmptyException extends DomainException {
    public AuthorNameEmptyException() {
        super("Author name cannot be empty");
    }
}
