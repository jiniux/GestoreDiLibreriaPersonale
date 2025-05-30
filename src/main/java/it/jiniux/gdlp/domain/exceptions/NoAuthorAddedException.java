package it.jiniux.gdlp.domain.exceptions;

public class NoAuthorAddedException extends DomainException {
    public NoAuthorAddedException() {
        super("At least one author must be added to the book");
    }
}
