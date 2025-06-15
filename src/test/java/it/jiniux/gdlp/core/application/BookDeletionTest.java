package it.jiniux.gdlp.core.application;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;
import java.util.Optional;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.GenreDto;
import it.jiniux.gdlp.core.domain.exceptions.BookDoesNotExistException;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryDataAccessProvider;
import org.junit.jupiter.api.Test;

public class BookDeletionTest {
    private DataAccessProvider createDataAccessProvider() {
        return new InMemoryDataAccessProvider();
    }

    private BookService createBookService() {
        DataAccessProvider dataAccessProvider = createDataAccessProvider();
        return new BookService(dataAccessProvider, new EventBus());
    }
    
    private BookDto createTestBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(List.of("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));
        
        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        editionDto.setPublisherName("Bompiani");
        
        bookDto.setEditions(List.of(editionDto));
        return bookDto;
    }
    
    @Test
    public void shouldDeleteBook() {
        BookService bookService = createBookService();
        
        BookDto bookDto = createTestBook();
        assertDoesNotThrow(() -> bookService.createBook(bookDto));
        
        Optional<BookDto> savedBook = assertDoesNotThrow(() -> bookService.findBookByIsbn("9788845292613"));
        assertTrue(savedBook.isPresent());
        
        String bookId = savedBook.get().getId();
        assertDoesNotThrow(() -> bookService.deleteBook(bookId));
        
        assertTrue(bookService.findBookByIsbn("9788845292613").isEmpty());
    }
    
    @Test
    public void shouldThrowWhenDeletingNonExistentBook() {
        BookService bookService = createBookService();

        assertThrows(BookDoesNotExistException.class, () -> bookService.deleteBook("8ca51161-55f6-47bd-a6be-7e7d3d938931"));
    }
    
    @Test
    public void shouldDeleteBookWithMultipleEditions() {
        BookService bookService = createBookService();
        
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(List.of("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));
        
        BookDto.Edition firstEdition = new BookDto.Edition();
        firstEdition.setIsbn("9788845292613");
        firstEdition.setPublisherName("Bompiani");
        firstEdition.setPublicationYear(1980);
        
        BookDto.Edition secondEdition = new BookDto.Edition();
        secondEdition.setIsbn("9788845296154");
        secondEdition.setPublisherName("Bompiani");
        secondEdition.setPublicationYear(2012);
        
        bookDto.setEditions(List.of(firstEdition, secondEdition));
        
        assertDoesNotThrow(() -> bookService.createBook(bookDto));
        
        Optional<BookDto> savedBook = assertDoesNotThrow(() -> bookService.findBookByIsbn("9788845292613"));
        assertTrue(savedBook.isPresent());

        String bookId = savedBook.get().getId();
        assertDoesNotThrow(() -> bookService.deleteBook(bookId));
        
        assertTrue(bookService.findBookByIsbn("9788845292613").isEmpty());
        assertTrue(bookService.findBookByIsbn("9788845296154").isEmpty());
    }
    
    @Test
    public void shouldDeleteBookWithEmptyEditionsList() {
        BookService bookService = createBookService();
        
        BookDto bookDto = createTestBook();
        assertDoesNotThrow(() -> bookService.createBook(bookDto));
        
        BookDto createdBook = bookService.findBookByIsbn("9788845292613").orElseThrow();
        
        createdBook.setEditions(List.of());
        assertDoesNotThrow(() -> bookService.editBook(createdBook));
        
        assertTrue(bookService.findBookByIsbn("9788845292613").isEmpty());
    }
}
