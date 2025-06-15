package it.jiniux.gdlp.core.application;
import static org.junit.jupiter.api.Assertions.*;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.BookSortByDto;
import it.jiniux.gdlp.core.application.dtos.GenreDto;
import it.jiniux.gdlp.core.application.dtos.ReadingStatusDto;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryDataAccessProvider;
import org.junit.jupiter.api.Test;

import java.util.List;

public class BookSearchTest {
    private BookService createBookServiceWithTestData() {
        BookService bookService = new BookService(new InMemoryDataAccessProvider(), new EventBus());

        BookDto harryPotter = new BookDto();
        harryPotter.setTitle("Harry Potter and the Philosopher's Stone");
        harryPotter.setAuthors(List.of("J.K. Rowling"));
        harryPotter.setGenres(List.of(new GenreDto("fantasy"), new GenreDto("young adult")));
        harryPotter.setReadingStatus(ReadingStatusDto.READ);

        BookDto.Edition hpEdition = new BookDto.Edition();
        hpEdition.setIsbn("9780747532743");
        hpEdition.setPublisherName("Bloomsbury");
        hpEdition.setEditionTitle("First UK Edition");
        hpEdition.setEditionNumber(1);
        hpEdition.setFormat("hardcover");
        hpEdition.setPublicationYear(1997);
        hpEdition.setLanguage("English");

        harryPotter.setEditions(List.of(hpEdition));
        assertDoesNotThrow(() -> bookService.createBook(harryPotter));

        BookDto lotr = new BookDto();
        lotr.setTitle("The Lord of the Rings");
        lotr.setAuthors(List.of("J.R.R. Tolkien"));
        lotr.setGenres(List.of(new GenreDto("fantasy"), new GenreDto("epic")));
        lotr.setReadingStatus(ReadingStatusDto.READING);

        BookDto.Edition lotrEdition = new BookDto.Edition();
        lotrEdition.setIsbn("9780618640157");
        lotrEdition.setPublisherName("Houghton Mifflin");
        lotrEdition.setEditionTitle("50th Anniversary Edition");
        lotrEdition.setEditionNumber(1);
        lotrEdition.setFormat("hardcover");
        lotrEdition.setPublicationYear(2004);
        lotrEdition.setLanguage("English");

        lotr.setEditions(List.of(lotrEdition));
        assertDoesNotThrow(() -> bookService.createBook(lotr));

        BookDto effectiveJava = new BookDto();
        effectiveJava.setTitle("Effective Java");
        effectiveJava.setAuthors(List.of("Joshua Bloch"));
        effectiveJava.setGenres(List.of(new GenreDto("programming"), new GenreDto("computer science")));
        effectiveJava.setReadingStatus(ReadingStatusDto.TO_READ);

        BookDto.Edition javaEdition = new BookDto.Edition();
        javaEdition.setIsbn("9780134685991");
        javaEdition.setPublisherName("Addison-Wesley");
        javaEdition.setEditionTitle("Third Edition");
        javaEdition.setEditionNumber(3);
        javaEdition.setFormat("paperback");
        javaEdition.setPublicationYear(2017);
        javaEdition.setLanguage("English");

        effectiveJava.setEditions(List.of(javaEdition));
        assertDoesNotThrow(() -> bookService.createBook(effectiveJava));

        BookDto prideAndPrejudice = new BookDto();
        prideAndPrejudice.setTitle("Pride and Prejudice");
        prideAndPrejudice.setAuthors(List.of("Jane Austen"));
        prideAndPrejudice.setGenres(List.of(new GenreDto("classic"), new GenreDto("romance")));
        prideAndPrejudice.setReadingStatus(ReadingStatusDto.READ);

        BookDto.Edition prideEdition = new BookDto.Edition();
        prideEdition.setIsbn("9780141439518");
        prideEdition.setPublisherName("Penguin Classics");
        prideEdition.setEditionTitle("Penguin Classics Edition");
        prideEdition.setEditionNumber(1);
        prideEdition.setFormat("paperback");
        prideEdition.setPublicationYear(2003);
        prideEdition.setLanguage("English");

        prideAndPrejudice.setEditions(List.of(prideEdition));
        assertDoesNotThrow(() -> bookService.createBook(prideAndPrejudice));

        return bookService;
    }

    @Test
    public void shouldFindBooksByTitle() {
        BookService bookService = createBookServiceWithTestData();

        // lowercase should work
        List<BookDto> results = bookService.findBooks("harry", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("Harry Potter and the Philosopher's Stone", results.getFirst().getTitle());

        results = bookService.findBooks("lord", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("The Lord of the Rings", results.getFirst().getTitle());

        results = bookService.findBooks("and", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(2, results.size());
    }

    @Test
    public void shouldFindBooksByAuthor() {
        BookService bookService = createBookServiceWithTestData();

        List<BookDto> results = bookService.findBooks("rowling", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("J.K. Rowling", results.getFirst().getAuthors().getFirst());

        results = bookService.findBooks("tolkien", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("J.R.R. Tolkien", results.getFirst().getAuthors().getFirst());
    }

    @Test
    public void shouldFindBooksByIsbn() {
        BookService bookService = createBookServiceWithTestData();

        List<BookDto> results = bookService.findBooks("9780134685991", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("Effective Java", results.getFirst().getTitle());

        results = bookService.findBooks("97801346", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("Effective Java", results.getFirst().getTitle());
    }

    @Test
    public void shouldFindBooksByEditionTitle() {
        BookService bookService = createBookServiceWithTestData();

        List<BookDto> results = bookService.findBooks("Third Edition", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("Effective Java", results.getFirst().getTitle());

        results = bookService.findBooks("50th Anniversary", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("The Lord of the Rings", results.getFirst().getTitle());
    }

    @Test
    public void shouldBeCaseInsensitive() {
        BookService bookService = createBookServiceWithTestData();

        List<BookDto> results = bookService.findBooks("harry", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("Harry Potter and the Philosopher's Stone", results.getFirst().getTitle());

        results = bookService.findBooks("TOLKIEN", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(1, results.size());
        assertEquals("J.R.R. Tolkien", results.getFirst().getAuthors().getFirst());
    }

    @Test
    public void shouldHandlePagination() {
        BookService bookService = createBookServiceWithTestData();

        Page<BookDto> page1 = bookService.findBooks("", 0, 2, BookSortByDto.TITLE);
        assertEquals(2, page1.getElements().size());
        assertEquals(2, page1.getSize());
        assertEquals(2, page1.getTotalPages());

        Page<BookDto> page2 = bookService.findBooks("", 1, 2, BookSortByDto.TITLE);
        assertEquals(2, page2.getElements().size());
        assertEquals(2, page2.getSize());
        assertEquals(2, page2.getTotalPages());

        assertNotEquals(page1.getElements().get(0).getId(), page2.getElements().get(0).getId());
        assertNotEquals(page1.getElements().get(1).getId(), page2.getElements().get(1).getId());
    }

    @Test
    public void shouldSortResultsByTitle() {
        BookService bookService = createBookServiceWithTestData();

        List<BookDto> results = bookService.findBooks("", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(4, results.size());

        assertEquals("Effective Java", results.get(0).getTitle());
        assertEquals("Harry Potter and the Philosopher's Stone", results.get(1).getTitle());
        assertEquals("Pride and Prejudice", results.get(2).getTitle());
        assertEquals("The Lord of the Rings", results.get(3).getTitle());
    }

    @Test
    public void shouldSortResultsByPublicationYear() {
        BookService bookService = createBookServiceWithTestData();

        List<BookDto> results = bookService.findBooks("", 0, 10, BookSortByDto.PUBLICATION_YEAR).getElements();
        assertEquals(4, results.size());

        assertEquals(1997, results.get(0).getEditions().getFirst().getPublicationYear());
        assertEquals(2003, results.get(1).getEditions().getFirst().getPublicationYear());
        assertEquals(2004, results.get(2).getEditions().getFirst().getPublicationYear());
        assertEquals(2017, results.get(3).getEditions().getFirst().getPublicationYear());
    }

    @Test
    public void shouldReturnEmptyListForNonMatchingQuery() {
        BookService bookService = createBookServiceWithTestData();

        List<BookDto> results = bookService.findBooks("ThisBookDoesNotExist", 0, 10, BookSortByDto.TITLE).getElements();
        assertEquals(0, results.size());
    }
}
