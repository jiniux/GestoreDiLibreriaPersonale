package it.jiniux.gdlp.core.domain;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import it.jiniux.gdlp.core.domain.Book.Title;
import it.jiniux.gdlp.core.domain.filters.Filter;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

public interface BookRepository {
    enum SortBy {
        TITLE, PUBLICATION_YEAR
    }

    @Getter
    @RequiredArgsConstructor
    class BookSearchResult {
        private final List<Book> books;

        public List<Book> getBooks() {
            return Collections.unmodifiableList(books);
        }

        private final long totalCount;
    }

    void saveBook(Book book);
    void deleteBook(Book book);

    Optional<Book> findBookByIsbn(Isbn isbn);

    Optional<Book> findBookById(Book.Id id);

    boolean existsBookById(Book.Id id);
    BookSearchResult findBooks(Filter<Book> filter, int page, int limit, SortBy sortBy);

    Set<Isbn> findAlreadyExistingIsbns(List<Isbn> isbns);

    Optional<Book> findBookByTitleAndAuthors(Title title, Set<Author> authors);

    Set<Author> findAllAuthorsContaining(String query, int limit);

    Set<Genre> findAllGenresContaining(String query, int limit);

    Set<Publisher> findAllPublishersContaining(String query, int limit);

    Set<Edition.Language> findAllLanguagesContaining(String query, int limit);
}