package it.jiniux.gdlp.domain.exceptions;

public class IsbnInvalidCheckDigitException extends DomainException {
    public IsbnInvalidCheckDigitException() {
        super("Invalid ISBN check digit");
    }
}
