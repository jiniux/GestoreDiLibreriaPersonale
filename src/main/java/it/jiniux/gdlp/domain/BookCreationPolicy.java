package it.jiniux.gdlp.domain;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.jiniux.gdlp.domain.exceptions.BookWithSameTitleAndAuthorsAlreadyExists;
import it.jiniux.gdlp.domain.exceptions.DomainException;
import it.jiniux.gdlp.domain.exceptions.IsbnAlreadyUsedByEditionException;

public class BookCreationPolicy {
    private final BookRepository bookRepository;

    public BookCreationPolicy(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private void validateEditionIsbns(Book book) throws IsbnAlreadyUsedByEditionException {
        Set<Isbn> bookIsbns = book.getIsbns();

        Set<Isbn> existingIsbns = bookRepository.findAlreadyExistingIsbns(List.copyOf(bookIsbns));
        Set<Isbn> duplicateIsbns = new HashSet<>();

        for (Isbn existingIsbn : existingIsbns) {
            if (bookIsbns.contains(existingIsbn)) {
                duplicateIsbns.add(existingIsbn);
            }
        }

        if (!duplicateIsbns.isEmpty()) {
            throw new IsbnAlreadyUsedByEditionException(duplicateIsbns);
        }
    }

    public void validateTitleAndAuthors(Book book) throws DomainException {
        Optional<Book> existingBook = bookRepository.findBookByTitleAndAuthors(book.getTitle(), book.getAuthors());
    
        if (existingBook.isPresent()) {
            throw new BookWithSameTitleAndAuthorsAlreadyExists(existingBook.get());
        }
    }

    public void validate(Book book) throws DomainException {
        validateTitleAndAuthors(book);
        validateEditionIsbns(book);
    }
}
