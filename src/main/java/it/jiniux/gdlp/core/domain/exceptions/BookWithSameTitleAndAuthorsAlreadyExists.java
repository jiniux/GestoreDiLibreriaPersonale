package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Book;
import lombok.Getter;

@Getter
public class BookWithSameTitleAndAuthorsAlreadyExists extends DomainException {
    private Book alreadyExistingBook;

    public BookWithSameTitleAndAuthorsAlreadyExists(Book alreadyExistingBook) {
        super("A book with the same title and authors already exists");
        this.alreadyExistingBook = alreadyExistingBook;
    }
    
}
