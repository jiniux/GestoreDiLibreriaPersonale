package it.jiniux.gdlp.domain.exceptions;

public class InvalidRatingException extends DomainException {
    public InvalidRatingException() {
        super("Rating must be between 1 and 5");
    }
}
