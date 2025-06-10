package it.jiniux.gdlp.core.application;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

public sealed class ApplicationEvent {
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class BookCreated extends ApplicationEvent {
        private BookDto bookDto;

        public BookCreated(BookDto bookDto) {
            this.bookDto = bookDto;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class BookUpdated extends ApplicationEvent {
        private BookDto bookDto;

        public BookUpdated(BookDto bookDto) {
            this.bookDto = bookDto;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class BookDeleted extends ApplicationEvent {
        private String bookId;

        public BookDeleted(String bookId) {
            this.bookId = bookId;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class EditionCoverUpdated extends ApplicationEvent {
        private String isbn;

        public EditionCoverUpdated(String isbn) {
            this.isbn = isbn;
        }
    }
}
