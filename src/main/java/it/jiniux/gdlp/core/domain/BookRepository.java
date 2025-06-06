package it.jiniux.gdlp.core.domain;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.jiniux.gdlp.core.domain.Book.Title;
import it.jiniux.gdlp.core.domain.filters.Filter;

public interface BookRepository {
    void saveBook(Book book);
    void deleteBook(Book book);

    Optional<Book> findBookByIsbn(Isbn isbn);
    Optional<Book> findBookById(Book.Id id);

    boolean existsBookById(Book.Id id);

    List<Book> filterBooks(Filter<Book> filter);

    Set<Isbn> findAlreadyExistingIsbns(List<Isbn> isbns);

    Optional<Book> findBookByTitleAndAuthors(Title title, Set<Author> authors);
}