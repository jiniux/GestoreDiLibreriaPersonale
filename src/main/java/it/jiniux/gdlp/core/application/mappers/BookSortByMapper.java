package it.jiniux.gdlp.core.application.mappers;

import it.jiniux.gdlp.core.application.dtos.BookSortByDto;
import it.jiniux.gdlp.core.domain.BookRepository;

public class BookSortByMapper {
    private static BookSortByMapper INSTANCE;

    private BookSortByMapper() {}

    public static synchronized BookSortByMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookSortByMapper();
        }
        return INSTANCE;
    }

    public BookRepository.SortBy toDomain(BookSortByDto bookSortByDto) {
        if (bookSortByDto == null) {
            return null;
        }
        return switch (bookSortByDto) {
            case TITLE -> BookRepository.SortBy.TITLE;
            case PUBLICATION_YEAR -> BookRepository.SortBy.PUBLICATION_YEAR;
            default -> throw new IllegalArgumentException("Unknown BookSortByDto: " + bookSortByDto);
        };
    }
}
