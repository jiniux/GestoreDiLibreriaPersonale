package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Edition;
import lombok.Getter;

@Getter
public class EditionAlreadyAddedException extends DomainException {
    private final Edition edition;

    public EditionAlreadyAddedException(Edition edition) {
        super("Edition has already been added to this book");

        this.edition = edition;
    }
}
