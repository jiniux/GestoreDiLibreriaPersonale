package it.jiniux.gdlp.infrastructure.json;

import it.jiniux.gdlp.core.domain.*;
import it.jiniux.gdlp.core.domain.exceptions.DomainException;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class JsonBookDataMapper {
    private JsonBookDataMapper() {}

    private static JsonBookDataMapper instance;

    public static synchronized JsonBookDataMapper getInstance() {
        if (instance == null) {
            instance = new JsonBookDataMapper();
        }
        return instance;
    }
    
    public JsonBookData toJsonBookData(Book book) {
        JsonBookData jsonBookData = new JsonBookData();
        
        // Map basic properties
        jsonBookData.setId(book.getId().getValue().toString());
        jsonBookData.setTitle(book.getTitle().getValue());
        book.getDescription().ifPresent(desc -> jsonBookData.setDescription(desc.getValue()));
        book.getRating().ifPresent(rating -> jsonBookData.setRating(rating.getValue()));
        jsonBookData.setReadingStatus(JsonReadingStatusData.valueOf(book.getReadingStatus().name()));
        
        // Map collections
        jsonBookData.setGenres(book.getGenres().stream()
            .map(g -> g.getName().getValue())
            .collect(Collectors.toList()));
        
        jsonBookData.setAuthors(book.getAuthors().stream()
            .map(a -> a.getName().getValue())
            .collect(Collectors.toList()));
        
        // Map editions
        jsonBookData.setEditions(book.getEditions().stream()
            .map(this::toJsonEdition)
            .collect(Collectors.toList()));
        
        return jsonBookData;
    }
    
    private JsonBookData.Edition toJsonEdition(Edition edition) {
        JsonBookData.Edition jsonEdition = new JsonBookData.Edition();
        
        jsonEdition.setIsbn(edition.getIsbn().getValue());
        jsonEdition.setPublisherName(edition.getPublisher().getName().getValue());
        
        edition.getEditionTitle().ifPresent(title -> jsonEdition.setEditionTitle(title.getValue()));
        edition.getEditionNumber().ifPresent(num -> jsonEdition.setEditionNumber(num.getValue()));
        edition.getFormat().ifPresent(format -> jsonEdition.setFormat(format.getValue()));
        edition.getLanguage().ifPresent(lang -> jsonEdition.setLanguage(lang.getValue()));
        edition.getPublicationYear().ifPresent(jsonEdition::setPublicationYear);
        
        List<String> additionalAuthors = edition.getAdditionalAuthors().stream()
            .map(a -> a.getName().getValue())
            .collect(Collectors.toList());
        jsonEdition.setAdditionalAuthors(additionalAuthors);
        
        return jsonEdition;
    }
    
    public Book toBook(JsonBookData jsonBookData) throws DomainException {
        Book.Builder bookBuilder = new Book.Builder(new Book.Title(jsonBookData.getTitle()));
        
        // Set basic properties
        if (jsonBookData.getId() != null) {
            bookBuilder.id(new Book.Id(jsonBookData.getId()));
        }
        
        if (jsonBookData.getDescription() != null) {
            bookBuilder.description(new Book.Description(jsonBookData.getDescription()));
        }
        
        if (jsonBookData.getRating() != null) {
            bookBuilder.rating(new Book.Rating(jsonBookData.getRating()));
        }
        
        if (jsonBookData.getReadingStatus() != null) {
            bookBuilder.readingStatus(ReadingStatus.valueOf(jsonBookData.getReadingStatus().name()));
        }
        
        // Add genres
        if (jsonBookData.getGenres() != null) {
            for (String genreValue : jsonBookData.getGenres()) {
                bookBuilder.addGenre(new Genre(new Genre.Name(genreValue)));
            }
        }
        
        // Add authors
        if (jsonBookData.getAuthors() != null) {
            for (String authorName : jsonBookData.getAuthors()) {
                bookBuilder.addAuthor(new Author(new Author.Name(authorName)));
            }
        }
        
        // Add editions
        if (jsonBookData.getEditions() != null) {
            for (JsonBookData.Edition jsonEdition : jsonBookData.getEditions()) {
                bookBuilder.addEdition(toEdition(jsonEdition));
            }
        }
        
        return bookBuilder.build();
    }
    
    private Edition toEdition(JsonBookData.Edition jsonEdition) {
        Isbn isbn = new Isbn(jsonEdition.getIsbn());
        Publisher publisher = new Publisher(new Publisher.Name(jsonEdition.getPublisherName()));
        
        Edition edition = new Edition(isbn, publisher);
        
        if (jsonEdition.getEditionTitle() != null) {
            edition.setEditionTitle(new Edition.EditionTitle(jsonEdition.getEditionTitle()));
        }
        
        if (jsonEdition.getEditionNumber() != null) {
            edition.setEditionNumber(new Edition.EditionNumber(jsonEdition.getEditionNumber()));
        }
        
        if (jsonEdition.getFormat() != null) {
            edition.setFormat(new Edition.Format(jsonEdition.getFormat()));
        }
        
        if (jsonEdition.getLanguage() != null) {
            edition.setLanguage(new Edition.Language(jsonEdition.getLanguage()));
        }
        
        if (jsonEdition.getPublicationYear() != null) {
            edition.setPublicationYear(jsonEdition.getPublicationYear());
        }
        
        if (jsonEdition.getAdditionalAuthors() != null) {
            for (String authorName : jsonEdition.getAdditionalAuthors()) {
                edition.addAdditionalAuthor(new Author(new Author.Name(authorName)));
            }
        }
        
        return edition;
    }
}
