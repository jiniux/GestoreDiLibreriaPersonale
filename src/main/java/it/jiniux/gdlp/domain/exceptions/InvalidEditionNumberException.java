package it.jiniux.gdlp.domain.exceptions;

public class InvalidEditionNumberException extends DomainException {
    public InvalidEditionNumberException() {
        super("Edition number must be greater than 0");
    }
}
