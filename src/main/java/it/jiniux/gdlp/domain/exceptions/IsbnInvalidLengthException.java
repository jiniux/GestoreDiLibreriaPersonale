package it.jiniux.gdlp.domain.exceptions;

public class IsbnInvalidLengthException extends DomainException {
    public IsbnInvalidLengthException() {
        super("ISBN must be 10 or 13 digits long");
    }
}
