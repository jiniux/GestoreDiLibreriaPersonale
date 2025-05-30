package it.jiniux.gdlp.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import org.junit.jupiter.api.Test;

import it.jiniux.gdlp.application.dtos.BookDto;
import it.jiniux.gdlp.application.dtos.GenreDto;
import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.BookRepository;
import it.jiniux.gdlp.domain.Isbn;
import it.jiniux.gdlp.domain.exceptions.BookWithSameTitleAndAuthorsAlreadyExists;
import it.jiniux.gdlp.domain.exceptions.IsbnAlreadyUsedByEditionException;
import it.jiniux.gdlp.domain.exceptions.NoEditionAddedException;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryBookRepository;

public class BookCreationTest {
    public BookRepository createBookRepository() {
        return new InMemoryBookRepository();
    }

    @Test
    public void shouldAddSimpleBook() {
        BookRepository bookRepository = createBookRepository();
        BookService bookService = new BookService(bookRepository);
        
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(Arrays.asList("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));
        
        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        editionDto.setPublisherName("Bompiani");
        
        bookDto.setEditions(Arrays.asList(editionDto));
        
        assertDoesNotThrow(() -> bookService.createBook(bookDto));
        
        Book savedBook = assertDoesNotThrow(() -> bookRepository.findBookByIsbn(new Isbn("9788845292613")).orElseThrow());

        assertEquals("Il nome della rosa", savedBook.getTitle().getValue());
        assertEquals(1, savedBook.getAuthors().size());
        assertEquals(1, savedBook.getGenres().size());
        assertEquals(1, savedBook.getEditions().size());
        assertEquals("9788845292613", savedBook.getEditions().iterator().next().getIsbn().getValue());
    }

    @Test
    public void shouldThrowIfBookAlreadyExists() {
        BookRepository bookRepository = createBookRepository();
        BookService bookService = new BookService(bookRepository);
        
        // Create example book
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(Arrays.asList("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));
        
        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        editionDto.setPublisherName("Bompiani");
        
        bookDto.setEditions(Arrays.asList(editionDto));
        
        // First creation should succeed
        assertDoesNotThrow(() -> bookService.createBook(bookDto));
        
        // Second creation with same title and authors should throw exception
        assertThrows(BookWithSameTitleAndAuthorsAlreadyExists.class, () -> bookService.createBook(bookDto));
    }

    @Test
    public void shouldThrowIfAtLeastOneEditionNotSpecified() {
        BookRepository bookRepository = createBookRepository();
        BookService bookService = new BookService(bookRepository);
        
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(Arrays.asList("Umberto Eco"));
        
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Mystery");
        bookDto.setGenres(Arrays.asList(genreDto));
        
        // No editions specified
        bookDto.setEditions(List.of());
        
        assertThrows(NoEditionAddedException.class, () -> bookService.createBook(bookDto));
    }

    @Test
    public void shouldThrowIfIsbnAlreadyUsed() {
        BookRepository bookRepository = createBookRepository();
        BookService bookService = new BookService(bookRepository);
        
        // Create first book
        BookDto bookDto1 = new BookDto();
        bookDto1.setTitle("Il nome della rosa");
        bookDto1.setAuthors(Arrays.asList("Umberto Eco"));
        bookDto1.setGenres(List.of(new GenreDto("mystery")));
        
        BookDto.Edition editionDto1 = new BookDto.Edition();
        editionDto1.setIsbn("9788845292613");
        editionDto1.setPublisherName("Bompiani");
        
        bookDto1.setEditions(Arrays.asList(editionDto1));
        
        assertDoesNotThrow(() -> bookService.createBook(bookDto1));
        
        // Create second book with same ISBN
        BookDto bookDto2 = new BookDto();
        bookDto2.setTitle("Il pendolo di Foucault");
        bookDto2.setAuthors(Arrays.asList("Umberto Eco"));
        
        GenreDto genreDto2 = new GenreDto();
        genreDto2.setName("Mystery");
        bookDto2.setGenres(Arrays.asList(genreDto2));
        
        BookDto.Edition editionDto2 = new BookDto.Edition();
        editionDto2.setIsbn("9788845292613"); // Same ISBN
        editionDto2.setPublisherName("Bompiani");
        
        bookDto2.setEditions(Arrays.asList(editionDto2));
        
        assertThrows(IsbnAlreadyUsedByEditionException.class, () -> bookService.createBook(bookDto2));
    }
}
