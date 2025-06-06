package it.jiniux.gdlp.core.application;
import static org.junit.jupiter.api.Assertions.*;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.GenreDto;
import it.jiniux.gdlp.core.domain.exceptions.BookWithSameTitleAndAuthorsAlreadyExists;
import it.jiniux.gdlp.core.domain.exceptions.IsbnAlreadyUsedByEditionException;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryDataAccessProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookEditTest {
    private DataAccessProvider createDataAccessProvider() {
        return new InMemoryDataAccessProvider();
    }

    private BookService createBookService() {
        DataAccessProvider dataAccessProvider = createDataAccessProvider();
        return new BookService(dataAccessProvider, new EventBus());
    }

    @Test
    public void shouldEditBook() {
        BookService bookService = createBookService();

        // Create a book to edit
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(List.of("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));

        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        editionDto.setPublisherName("Bompiani");

        bookDto.setEditions(List.of(editionDto));

        assertDoesNotThrow(() -> bookService.createBook(bookDto));

        BookDto newBook = assertDoesNotThrow(() -> bookService.findBookByIsbn("9788845292613").orElseThrow());

        // Edit the book
        newBook.setTitle("Il nome della rosa - Edited");
        assertDoesNotThrow(() -> bookService.editBook(newBook));

        // Verify the changes
        BookDto editedBook = assertDoesNotThrow(() -> bookService.findBookByIsbn("9788845292613").orElseThrow());
        assertEquals("Il nome della rosa - Edited", editedBook.getTitle());
    }

    @Test
    public void shouldThrowIfNewBookHasAlreadyUsedIsbn() {
        BookService bookService = createBookService();

        // Create a book to edit
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(List.of("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));

        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        editionDto.setPublisherName("Bompiani");

        bookDto.setEditions(List.of(editionDto));

        assertDoesNotThrow(() -> bookService.createBook(bookDto));

        // Create another book with the same ISBN
        BookDto anotherBook = new BookDto();
        anotherBook.setTitle("Il pendolo di Foucault");
        anotherBook.setAuthors(List.of("Umberto Eco"));
        anotherBook.setGenres(List.of(new GenreDto("mystery")));

        BookDto.Edition anotherEdition = new BookDto.Edition();
        anotherEdition.setIsbn("8846200993"); // Same ISBN as the first book
        anotherEdition.setPublisherName("Bompiani");

        anotherBook.setEditions(List.of(anotherEdition));

        assertDoesNotThrow(() -> bookService.createBook(anotherBook));

        BookDto newAnotherBook = assertDoesNotThrow(() -> bookService.findBookByIsbn("8846200993").orElseThrow());

        newAnotherBook.getEditions().getFirst().setIsbn("9788845292613"); // Trying to set the same ISBN as the first book

        assertThrows(IsbnAlreadyUsedByEditionException.class, () -> bookService.editBook(newAnotherBook));
    }

    @Test
    public void shouldThrowIfNewBookHasAlreadyUsedTitleAndAuthors() {
        BookService bookService = createBookService();

        // Create a book to edit
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        bookDto.setAuthors(List.of("Umberto Eco"));
        bookDto.setGenres(List.of(new GenreDto("mystery")));

        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        editionDto.setPublisherName("Bompiani");

        bookDto.setEditions(List.of(editionDto));

        assertDoesNotThrow(() -> bookService.createBook(bookDto));

        // Create another book with the same title and authors
        BookDto anotherBook = new BookDto();
        anotherBook.setTitle("Harry potter e la pietra filosofale");
        anotherBook.setAuthors(List.of("J.K. Rowling"));
        anotherBook.setGenres(List.of(new GenreDto("fantasy")));

        BookDto.Edition anotherEdition = new BookDto.Edition();
        anotherEdition.setIsbn("8846200993");
        anotherEdition.setPublisherName("Bompiani");

        anotherBook.setEditions(List.of(anotherEdition));

        assertDoesNotThrow(() -> bookService.createBook(anotherBook));

        BookDto newAnotherBook = assertDoesNotThrow(() -> bookService.findBookByIsbn("8846200993").orElseThrow());
        newAnotherBook.setTitle("Il nome della rosa");
        newAnotherBook.setAuthors(List.of("Umberto Eco"));

        assertThrows(BookWithSameTitleAndAuthorsAlreadyExists.class, () -> bookService.editBook(newAnotherBook));
    }
}

