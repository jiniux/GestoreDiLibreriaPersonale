package it.jiniux.gdlp.application;

import it.jiniux.gdlp.application.dtos.BookDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

public sealed class Event {
    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class BookCreated extends Event {
        private BookDto bookDto;

        public BookCreated(BookDto bookDto) {
            this.bookDto = bookDto;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class BookUpdated extends Event {
        private BookDto bookDto;

        public BookUpdated(BookDto bookDto) {
            this.bookDto = bookDto;
        }
    }

    @EqualsAndHashCode(callSuper = true)
    @Data
    public static final class BookDeleted extends Event {
        private String bookId;

        public BookDeleted(String bookId) {
            this.bookId = bookId;
        }
    }
}
