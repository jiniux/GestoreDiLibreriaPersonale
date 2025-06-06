package it.jiniux.gdlp.core.domain;

import java.util.*;

import it.jiniux.gdlp.core.domain.exceptions.*;
import lombok.*;

@Getter
@EqualsAndHashCode(of = "id")
public class Book {
    @Value
    public static class Title {
        String value;

        public Title(String value) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("Title cannot be null or empty");
            }

            this.value = value;
        }
    }

    @Value
    public static class Description {
        String value;

        public Description(String value) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("Description cannot be null or empty");
            }

            this.value = value;
        }
    }
    
    @Value
    public static class Rating {
        int value;

        public Rating(int value) {
            if (value < 1 || value > 5) {
                throw new IllegalArgumentException("Rating must be between 1 and 5");
            }
            this.value = value;
        }
    }

    @Value
    public static class Id {
        UUID value;

        public Id() {
            this.value = UUID.randomUUID();
        }

        public Id(UUID value) {
            if (value == null) {
                throw new IllegalArgumentException("Book ID cannot be null");
            }

            this.value = value;
        }

        public Id(String value) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("Book ID cannot be null or empty");
            }

            this.value = UUID.fromString(value);
        }
    }

    @Setter(AccessLevel.PRIVATE)
    private Id id;

    private final Title title;
    private final Set<Edition> editions;
    private final Set<Author> authors;
    private final Set<Genre> genres;
    
    @Setter
    private Description description;
    
    @Setter
    private Rating rating;
    
    @Setter
    private ReadingStatus readingStatus;

    public Book(Title title, List<Edition> editions, List<Author> authors, List<Genre> genres) throws DomainException {
        this.title = title;

        if (editions.isEmpty()) {
            throw new NoEditionAddedException();
        }
        if (authors.isEmpty()) {
            throw new NoAuthorAddedException();
        }
        if (genres.isEmpty()) {
            throw new NoGenreAddedException();
        }

        this.editions = new HashSet<>();
        this.authors = new HashSet<>();
        this.genres = new HashSet<>(genres);

        for (Edition edition : editions) {
            this.addEdition(edition);
        }

        for (Author author : authors) {
            this.addAuthor(author);
        }

        this.id = new Id(UUID.randomUUID());
        this.readingStatus = ReadingStatus.TO_READ;
    }

    public Optional<Description> getDescription() {
        return Optional.ofNullable(description);
    }

    public Optional<Rating> getRating() {
        return Optional.ofNullable(rating);
    }
    
    public void addEdition(Edition edition) throws EditionAlreadyAddedException {
        if (this.editions.contains(edition)) {
            throw new EditionAlreadyAddedException(edition);
        }
        this.editions.add(edition.clone());
    }

    public void addAuthor(Author author) throws AuthorAlreadyAddedException {
        if (this.authors.contains(author)) {
            throw new AuthorAlreadyAddedException(author);
        }

        this.authors.add(author);
    }
    
    public void addGenre(Genre genre) {
        this.genres.add(genre);
    }

    public Set<Isbn> getIsbns() {
        Set<Isbn> isbns = new HashSet<>();

        for (Edition edition : editions) {
            isbns.add(edition.getIsbn());
        }
        
        return isbns;
    }

    public static class Builder {
        private final Title title;
        private Description description;
        private Rating rating;
        private ReadingStatus readingStatus;
        private Id id;

        private final List<Edition> editions;
        private final List<Author> authors;
        private final List<Genre> genres;

        public Builder(Title title) {
            this.title = title;
            this.editions = new ArrayList<>();
            this.authors = new ArrayList<>();
            this.genres = new ArrayList<>();
        }

        public Builder description(Description description) {
            this.description = description;
            return this;
        }
        
        public Builder rating(Rating rating) {
            this.rating = rating;
            return this;
        }
        
        public Builder readingStatus(ReadingStatus readingStatus) {
            this.readingStatus = readingStatus;
            return this;
        }

        public Builder addEdition(Edition edition) {
            this.editions.add(edition);
            return this;
        }

        public Builder addAuthor(Author author) {
            this.authors.add(author);
            return this;
        }
        
        public Builder addGenre(Genre genre) {
            this.genres.add(genre);
            return this;
        }

        public Builder id(Id id) {
            this.id = id;
            return this;
        }

        public Book build() throws DomainException {
            Book book = new Book(title, editions, authors, genres);

            book.setDescription(description);
            book.setRating(rating);

            if (id != null) {
                book.setId(id);
            }

            if (readingStatus != null) {
                book.setReadingStatus(readingStatus);
            }

            return book;
        }
    }
}
