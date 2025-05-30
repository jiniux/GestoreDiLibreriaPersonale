package it.jiniux.gdlp.domain.exceptions;

public class EditionTitleEmptyException extends DomainException {
    public EditionTitleEmptyException() {
        super("Edition title cannot be empty");
    }
}
