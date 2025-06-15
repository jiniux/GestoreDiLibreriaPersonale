package it.jiniux.gdlp.core.application.mappers;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.GenreDto;
import it.jiniux.gdlp.core.domain.*;
import it.jiniux.gdlp.core.domain.exceptions.DomainException;

import java.util.ArrayList;
import java.util.List;

public class BookMapper {
    private static BookMapper INSTANCE;

    private BookMapper() {
        // Private constructor to enforce singleton pattern
    }

    public static synchronized BookMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookMapper();
        }

        return INSTANCE;
    }
    
    public Book toDomain(BookDto request) throws DomainException {
        Book.Title title = new Book.Title(request.getTitle());
        
        List<Author> authors = mapAuthors(request.getAuthors());
        
        List<Genre> genres = request.getGenres().stream().map(g -> GenreMapper.getInstance().toDomain(g)).toList();

        List<Edition> editions = mapEditions(request.getEditions());
        
        Book.Builder bookBuilder = new Book.Builder(title);
        
        if (request.getDescription() != null && !request.getDescription().isEmpty()) {
            bookBuilder.description(new Book.Description(request.getDescription()));
        }
        
        if (request.getRating() != null) {
            bookBuilder.rating(new Book.Rating(request.getRating()));
        }
        
        if (request.getReadingStatus() != null) {
            bookBuilder.readingStatus(ReadingStatusMapper.getInstance().toDomain(request.getReadingStatus()));
        }

        for (Genre genre : genres) {
            bookBuilder.addGenre(genre);
        }

        for (Author author : authors) {
            bookBuilder.addAuthor(author);
        }

        for (Edition edition : editions) {
            bookBuilder.addEdition(edition);
        }

        if (request.getId() != null && !request.getId().isEmpty()) {
            bookBuilder.id(new Book.Id(request.getId()));
        }

        return bookBuilder.build();
    }
    
    private List<Author> mapAuthors(List<String> authorRequests) throws DomainException {
        List<Author> authors = new ArrayList<>();
        
        for (String authorRequest : authorRequests) {
            authors.add(new Author(new Author.Name(authorRequest)));
        }
        
        return authors;
    }
    
    private List<Edition> mapEditions(List<BookDto.Edition> editionRequests) throws DomainException {
        List<Edition> editions = new ArrayList<>();
        
        for (BookDto.Edition editionRequest : editionRequests) 
        {
            Isbn isbn = new Isbn(editionRequest.getIsbn());
            Publisher publisher = new Publisher(new Publisher.Name(editionRequest.getPublisherName()));

            Edition edition = new Edition(isbn, publisher);

            if (editionRequest.getEditionTitle() != null) {
                edition.setEditionTitle(new Edition.EditionTitle(editionRequest.getEditionTitle()));
            }

            if (editionRequest.getEditionNumber() != null) {
                edition.setEditionNumber(new Edition.EditionNumber(editionRequest.getEditionNumber()));
            }

            if (editionRequest.getFormat() != null) {
                edition.setFormat(new Edition.Format(editionRequest.getFormat()));
            }

            if (editionRequest.getLanguage() != null) {
                edition.setLanguage(new Edition.Language(editionRequest.getLanguage()));
            }

            if (editionRequest.getPublicationYear() != null) {
                edition.setPublicationYear(new Edition.PublicationYear(editionRequest.getPublicationYear()));
            }

            if (editionRequest.getAdditionalAuthors() != null) {
                for (String authorRequest : editionRequest.getAdditionalAuthors()) {
                    Author additionalAuthor = new Author(new Author.Name(authorRequest));
                    edition.addAdditionalAuthor(additionalAuthor);
                }
            }
            
            editions.add(edition);
        }
        
        return editions;
    }

    public BookDto toDto(Book book) {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(book.getTitle().getValue());
        bookDto.setDescription(book.getDescription().map(Book.Description::getValue).orElse(null));
        bookDto.setRating(book.getRating().map(Book.Rating::getValue).orElse(null));
        bookDto.setReadingStatus(ReadingStatusMapper.getInstance().toDto(book.getReadingStatus()));
        
        List<String> authors = new ArrayList<>();
        for (Author author : book.getAuthors()) {
            authors.add(author.getName().getValue());
        }
        bookDto.setAuthors(authors);
        
        List<GenreDto> genres = new ArrayList<>();
        for (Genre genre : book.getGenres()) {
            genres.add(GenreMapper.getInstance().toDto(genre));
        }
        bookDto.setGenres(genres);
        
        List<BookDto.Edition> editions = new ArrayList<>();
        for (Edition edition : book.getEditions()) {
            BookDto.Edition editionDto = new BookDto.Edition();
            editionDto.setIsbn(edition.getIsbn().getValue());
            editionDto.setPublisherName(edition.getPublisher().getName().getValue());
            editionDto.setEditionTitle(edition.getEditionTitle().map(Edition.EditionTitle::getValue).orElse(null));
            editionDto.setEditionNumber(edition.getEditionNumber().map(Edition.EditionNumber::getValue).orElse(null));
            editionDto.setFormat(edition.getFormat().map(Edition.Format::getValue).orElse(null));
            editionDto.setLanguage(edition.getLanguage().map(Edition.Language::getValue).orElse(null));
            editionDto.setPublicationYear(edition.getPublicationYear().map(Edition.PublicationYear::getValue).orElse(null));

            List<String> additionalAuthors = new ArrayList<>();
            for (Author additionalAuthor : edition.getAdditionalAuthors()) {
                additionalAuthors.add(additionalAuthor.getName().getValue());
            }
            editionDto.setAdditionalAuthors(additionalAuthors);
            
            editions.add(editionDto);
        }
        
        bookDto.setEditions(editions);
        bookDto.setId(book.getId().getValue().toString());
        
        return bookDto;
    }
}
