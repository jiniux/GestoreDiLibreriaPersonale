package it.jiniux.gdlp.domain.exceptions;

public class FormatEmptyException extends DomainException {
    public FormatEmptyException() {
        super("Format cannot be empty");
    }
}
