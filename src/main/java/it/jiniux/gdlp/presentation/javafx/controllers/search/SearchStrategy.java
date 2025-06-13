package it.jiniux.gdlp.presentation.javafx.controllers.search;


import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.core.application.Page;
import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.BookSortByDto;

public interface SearchStrategy {
    Page<BookDto> search(BookService bookService, int page, int limit, BookSortByDto sorting);
}