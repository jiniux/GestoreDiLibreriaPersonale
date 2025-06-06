package it.jiniux.gdlp.core.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import it.jiniux.gdlp.infrastructure.inmemory.InMemoryDataAccessProvider;
import org.junit.jupiter.api.Test;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.GenreDto;
import it.jiniux.gdlp.core.domain.exceptions.BookWithSameTitleAndAuthorsAlreadyExists;
import it.jiniux.gdlp.core.domain.exceptions.IsbnAlreadyUsedByEditionException;
import it.jiniux.gdlp.core.domain.exceptions.NoEditionAddedException;

public class BookCreationTest {
    private DataAccessProvider createDataAccessProvider() {
        return new InMemoryDataAccessProvider();
    }

    private BookService createBookService() {
        DataAccessProvider dataAccessProvider = createDataAccessProvider();
        return new BookService(dataAccessProvider, new EventBus());
    }

    @Test
    public void shouldAddSimpleBook() {
        BookService bookService = createBookService();
        
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(List.of("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));
        
        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        editionDto.setPublisherName("Bompiani");
        
        bookDto.setEditions(List.of(editionDto));
        
        assertDoesNotThrow(() -> bookService.createBook(bookDto));
        
        BookDto savedBook = assertDoesNotThrow(() -> bookService.findBookByIsbn("9788845292613").orElseThrow());

        assertEquals("Il nome della rosa", savedBook.getTitle());
        assertEquals(1, savedBook.getAuthors().size());
        assertEquals(1, savedBook.getGenres().size());
        assertEquals(1, savedBook.getEditions().size());
        assertEquals("9788845292613", savedBook.getEditions().getFirst().getIsbn());
    }

    @Test
    public void shouldThrowIfBookAlreadyExists() {
        BookService bookService = createBookService();
        
        // Create example book
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(List.of("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));
        
        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        editionDto.setPublisherName("Bompiani");
        
        bookDto.setEditions(List.of(editionDto));
        
        // First creation should succeed
        assertDoesNotThrow(() -> bookService.createBook(bookDto));
        
        // Second creation with same title and authors should throw exception
        assertThrows(BookWithSameTitleAndAuthorsAlreadyExists.class, () -> bookService.createBook(bookDto));
    }

    @Test
    public void shouldThrowIfAtLeastOneEditionNotSpecified() {
        BookService bookService = createBookService();

        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(List.of("Umberto Eco"));
        
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Mystery");
        bookDto.setGenres(List.of(genreDto));
        
        // No editions specified
        bookDto.setEditions(List.of());
        
        assertThrows(NoEditionAddedException.class, () -> bookService.createBook(bookDto));
    }

    @Test
    public void shouldThrowIfIsbnAlreadyUsed() {
        BookService bookService = createBookService();
        
        // Create first book
        BookDto bookDto1 = new BookDto();
        bookDto1.setTitle("Il nome della rosa");
        bookDto1.setAuthors(List.of("Umberto Eco"));
        bookDto1.setGenres(List.of(new GenreDto("mystery")));
        
        BookDto.Edition editionDto1 = new BookDto.Edition();
        editionDto1.setIsbn("9788845292613");
        editionDto1.setPublisherName("Bompiani");
        
        bookDto1.setEditions(List.of(editionDto1));
        
        assertDoesNotThrow(() -> bookService.createBook(bookDto1));
        
        // Create second book with same ISBN
        BookDto bookDto2 = new BookDto();
        bookDto2.setTitle("Il pendolo di Foucault");
        bookDto2.setAuthors(List.of("Umberto Eco"));
        
        GenreDto genreDto2 = new GenreDto();
        genreDto2.setName("Mystery");
        bookDto2.setGenres(List.of(genreDto2));
        
        BookDto.Edition editionDto2 = new BookDto.Edition();
        editionDto2.setIsbn("9788845292613"); // Same ISBN
        editionDto2.setPublisherName("Bompiani");
        
        bookDto2.setEditions(List.of(editionDto2));
        
        assertThrows(IsbnAlreadyUsedByEditionException.class, () -> bookService.createBook(bookDto2));
    }
}
