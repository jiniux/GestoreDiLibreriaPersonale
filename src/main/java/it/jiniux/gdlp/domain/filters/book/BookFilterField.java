package it.jiniux.gdlp.domain.filters.book;

import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.Edition;
import it.jiniux.gdlp.domain.ReadingStatus;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.Iterator;
import java.util.Locale;
import java.util.stream.Stream;

public enum BookFilterField {
    TITLE {
        @Override
        public Iterator<Object> createIterator(Book book) {
            return Stream.of((Object) book.getTitle().getValue()).iterator();
        };

        @Override
        boolean supportsReferenceClass(Class<?> c) {
            return c == String.class;
        }
    },
    ANY_ISBN {
        @Override
        public Iterator<Object> createIterator(Book book) {
            return book.getIsbns().stream().map(i -> (Object) i.getValue()).iterator();
        };

        @Override
        boolean supportsReferenceClass(Class<?> c) {
            return c == String.class;
        }
    },
    ANY_GENRE {
        @Override
        public Iterator<Object> createIterator(Book book) {
            return book.getGenres().stream().map(g -> (Object) g.getName().getValue()).iterator();
        }

        @Override
        boolean supportsReferenceClass(Class<?> c) {
            return c == String.class;
        }
    },
    ANY_AUTHOR_NAME {
        @Override
        public Iterator<Object> createIterator(Book book) {
            return book.getAuthors().stream().map(a -> (Object) a).iterator();
        }

        @Override
        boolean supportsReferenceClass(Class<?> c) {
            return c == String.class;
        }
    },
    ANY_PUBLICATION_YEAR {
        @Override
        public Iterator<Object> createIterator(Book book) {
            return book.getEditions().stream().map(y -> (Object)
                    y.getPublicationYear().orElse(null)
            ).iterator();
        }

        @Override
        boolean supportsReferenceClass(Class<?> c) {
            return c == Integer.class;
        }
    },
    ANY_PUBLISHER_NAME {
        @Override
        public Iterator<Object> createIterator(Book book) {
            return book.getEditions().stream().map(
                    e -> (Object) e.getPublisher().getName().getValue()
            ).iterator();
        }

        @Override
        boolean supportsReferenceClass(Class<?> c) {
            return c == String.class;
        }
    },
    ANY_LANGUAGE {
        @Override
        public Iterator<Object> createIterator(Book book) {
            return book.getEditions().stream()
                    .map(e ->
                            (Object) e.getLanguage().map(Edition.Language::getValue).orElse(null)
                    ).iterator();
        }

        @Override
        boolean supportsReferenceClass(Class<?> c) {
            return c == String.class;
        }
    },
    READING_STATUS {
        @Override
        public Iterator<Object> createIterator(Book book) {
            return Stream.of((Object) book.getReadingStatus()).iterator();
        }

        @Override
        boolean supportsReferenceClass(Class<?> c) {
            return c == ReadingStatus.class;
        }
    };

    public Iterator<Object> createIterator(Book book) {
        throw new UnsupportedOperationException("This field does not support iteration");
    }

    boolean supportsReferenceClass(Class<?> c) {
        throw new UnsupportedOperationException("This field does not support reference class checks");
    }
}
