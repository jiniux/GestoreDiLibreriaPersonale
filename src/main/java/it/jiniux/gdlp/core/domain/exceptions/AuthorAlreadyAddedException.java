package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Author;
import lombok.Getter;

@Getter
public class AuthorAlreadyAddedException extends DomainException {
    private final String authorName;

    public AuthorAlreadyAddedException(Author author) {
        super("Author has already been added to this book");
        this.authorName = author.getName().getValue();
    }
}
