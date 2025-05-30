package it.jiniux.gdlp.domain.exceptions;

public class BookTitleEmptyException extends DomainException {
    public BookTitleEmptyException() {
        super("Title cannot be empty");
    }
}
