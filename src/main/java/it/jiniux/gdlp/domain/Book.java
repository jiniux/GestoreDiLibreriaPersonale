package it.jiniux.gdlp.domain;

import java.util.*;

import it.jiniux.gdlp.domain.exceptions.*;
import lombok.*;

@Getter
public class Book {
    @Value
    public static class Title {
        String value;

        public Title(String value) throws BookTitleEmptyException {
            if (value == null || value.isEmpty()) {
                throw new BookTitleEmptyException();
            }

            this.value = value;
        }
    }

    @Value
    public static class Description {
        String value;

        public Description(String value) throws BookDescriptionEmptyException {
            if (value == null || value.isEmpty()) {
                throw new BookDescriptionEmptyException();
            }

            this.value = value;
        }
    }
    
    @Value
    public static class Rating {
        int value;

        public Rating(int value) throws InvalidRatingException {
            if (value < 1 || value > 5) {
                throw new InvalidRatingException();
            }
            this.value = value;
        }
    }

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

        public Book build() throws DomainException {
            Book book = new Book(title, editions, authors, genres);
            book.setDescription(description);
            book.setRating(rating);

            if (readingStatus == null) {
                readingStatus = ReadingStatus.TO_READ;
            }
            
            return book;
        }
    }
}
