package it.jiniux.gdlp.application;
import static org.junit.jupiter.api.Assertions.*;

import it.jiniux.gdlp.application.dtos.BookDto;
import it.jiniux.gdlp.application.dtos.GenreDto;
import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.BookRepository;
import it.jiniux.gdlp.domain.Isbn;
import it.jiniux.gdlp.domain.exceptions.BookWithSameTitleAndAuthorsAlreadyExists;
import it.jiniux.gdlp.domain.exceptions.IsbnAlreadyUsedByEditionException;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryBookRepository;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookEditTest {
    private BookRepository createBookRepository() {
        return new InMemoryBookRepository();
    }

    @Test
    public void shouldEditBook() {
        BookRepository bookRepository = createBookRepository();
        BookService bookService = new BookService(bookRepository);

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
        Book editedBook = assertDoesNotThrow(() -> bookRepository.findBookByIsbn(new Isbn("9788845292613")).orElseThrow());
        assertEquals("Il nome della rosa - Edited", editedBook.getTitle().getValue());
    }

    @Test
    public void shouldThrowIfNewBookHasAlreadyUsedIsbn() {
        BookRepository bookRepository = createBookRepository();
        BookService bookService = new BookService(bookRepository);

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
        BookRepository bookRepository = createBookRepository();
        BookService bookService = new BookService(bookRepository);

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

