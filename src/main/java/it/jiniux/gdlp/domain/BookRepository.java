package it.jiniux.gdlp.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.jiniux.gdlp.domain.Book.Title;

public interface BookRepository {
    void saveBook(Book book);
    void deleteBook(Book book);

    Optional<Book> findBookByIsbn(Isbn isbn);

    Set<Isbn> findAlreadyExistingIsbns(List<Isbn> isbns);

    Optional<Book> findBookByTitleAndAuthors(Title title, Set<Author> authors);
}