package it.jiniux.gdlp.presentation.javafx.controllers.search.strategies;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.core.application.Page;
import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.BookSortByDto;
import it.jiniux.gdlp.presentation.javafx.controllers.search.SearchStrategy;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class QuerySearchStrategy implements SearchStrategy {
    private final String query;

    @Override
    public Page<BookDto> search(BookService bookService, int page, int limit, BookSortByDto sorting) {
        return bookService.findBooks(query, page, limit, sorting);
    }
}
