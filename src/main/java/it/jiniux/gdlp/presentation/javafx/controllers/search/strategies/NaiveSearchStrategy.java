package it.jiniux.gdlp.presentation.javafx.controllers.search.strategies;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.core.application.Page;
import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.BookFilterDto;
import it.jiniux.gdlp.core.application.dtos.BookSortByDto;
import it.jiniux.gdlp.presentation.javafx.controllers.search.SearchStrategy;

public class NaiveSearchStrategy implements SearchStrategy {
    private static NaiveSearchStrategy INSTANCE;

    private NaiveSearchStrategy() {}

    public synchronized static NaiveSearchStrategy getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new NaiveSearchStrategy();
        }
        return INSTANCE;
    }

    @Override
    public Page<BookDto> search(BookService bookService, int page, int limit, BookSortByDto sorting) {
        return bookService.findBooks(page, limit, sorting);
    }
}
