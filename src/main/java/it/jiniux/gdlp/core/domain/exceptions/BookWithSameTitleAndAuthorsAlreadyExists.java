package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Book;
import lombok.Getter;

@Getter
public class BookWithSameTitleAndAuthorsAlreadyExists extends DomainException {
    private String bookId;
    private String bookTitle;

    public BookWithSameTitleAndAuthorsAlreadyExists(Book alreadyExistingBook) {
        super("A book with the same title and authors already exists");
        this.bookId = alreadyExistingBook.getId().getValue().toString();
        this.bookTitle = alreadyExistingBook.getTitle().getValue();
    }
    
}
