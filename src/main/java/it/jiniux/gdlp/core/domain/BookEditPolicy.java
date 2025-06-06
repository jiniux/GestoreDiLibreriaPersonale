package it.jiniux.gdlp.core.domain;

import it.jiniux.gdlp.core.domain.exceptions.BookDoesNotExistException;
import it.jiniux.gdlp.core.domain.exceptions.BookWithSameTitleAndAuthorsAlreadyExists;
import it.jiniux.gdlp.core.domain.exceptions.DomainException;
import it.jiniux.gdlp.core.domain.exceptions.IsbnAlreadyUsedByEditionException;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

public class BookEditPolicy {
    private final BookRepository bookRepository;

    public BookEditPolicy(BookRepository bookRepository) {
        this.bookRepository = bookRepository;
    }

    private void validateEditionIsbns(Book oldBook, Book newBook) throws IsbnAlreadyUsedByEditionException {
        Set<Isbn> bookIsbns = newBook.getIsbns();
        Set<Isbn> oldIsbns = oldBook.getIsbns();

        Set<Isbn> existingIsbns = bookRepository.findAlreadyExistingIsbns(List.copyOf(bookIsbns));
        Set<Isbn> duplicateIsbns = new HashSet<>();

        for (Isbn existingIsbn : existingIsbns) {
            if (bookIsbns.contains(existingIsbn) && !oldIsbns.contains(existingIsbn)) {
                duplicateIsbns.add(existingIsbn);
            }
        }

        if (!duplicateIsbns.isEmpty()) {
            throw new IsbnAlreadyUsedByEditionException(duplicateIsbns);
        }
    }

    public void validateTitleAndAuthors(Book oldBook, Book newBook) throws DomainException {
        if (oldBook.getTitle().equals(newBook.getTitle()) && oldBook.getAuthors().equals(newBook.getAuthors())) {
            return; // No change in title and authors, no need to validate
        }

        Optional<Book> existingBook = bookRepository.findBookByTitleAndAuthors(newBook.getTitle(), newBook.getAuthors());

        if (existingBook.isPresent()) {
            throw new BookWithSameTitleAndAuthorsAlreadyExists(existingBook.get());
        }
    }

    public void validate(Book newBook) throws DomainException {
        Book oldBook = bookRepository.findBookById(newBook.getId())
                .orElseThrow(() -> new BookDoesNotExistException(newBook.getId()));

        validateTitleAndAuthors(oldBook, newBook);
        validateEditionIsbns(oldBook, newBook);
    }
}
