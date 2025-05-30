package it.jiniux.gdlp.domain.exceptions;

public class NoEditionAddedException extends DomainException {
    public NoEditionAddedException() {
        super("At least one edition must be added to the book");
    }
}
