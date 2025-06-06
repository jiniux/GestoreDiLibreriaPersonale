package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Author;
import lombok.Getter;

@Getter
public class AuthorAlreadyAddedException extends DomainException {
    private final Author author;

    public AuthorAlreadyAddedException(Author author) {
        super("Author has already been added to this book");
        this.author = author;
    }
}
