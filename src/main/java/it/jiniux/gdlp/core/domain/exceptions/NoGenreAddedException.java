package it.jiniux.gdlp.core.domain.exceptions;

public class NoGenreAddedException extends DomainException {
    public NoGenreAddedException() {
        super("At least one genre must be added to the book");
    }
}
