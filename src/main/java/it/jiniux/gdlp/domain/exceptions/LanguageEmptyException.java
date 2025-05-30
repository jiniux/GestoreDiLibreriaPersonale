package it.jiniux.gdlp.domain.exceptions;

public class LanguageEmptyException extends DomainException {
    public LanguageEmptyException() {
        super("Language cannot be empty");
    }
}
