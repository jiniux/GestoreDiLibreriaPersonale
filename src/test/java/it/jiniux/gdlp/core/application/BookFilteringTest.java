package it.jiniux.gdlp.core.application;
import static org.junit.jupiter.api.Assertions.*;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.BookFilterDto;
import it.jiniux.gdlp.core.application.dtos.GenreDto;
import it.jiniux.gdlp.core.application.dtos.ReadingStatusDto;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryDataAccessProvider;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

public class BookFilteringTest {
    private BookService createBookServiceWithTestData() {
        BookService bookService = new BookService(new InMemoryDataAccessProvider(), new EventBus());

        // Book 1: Dune
        BookDto dune = new BookDto();
        dune.setTitle("Dune");
        dune.setAuthors(List.of("Frank Herbert"));
        dune.setGenres(List.of(new GenreDto("scifi")));
        dune.setReadingStatus(ReadingStatusDto.READING);

        BookDto.Edition duneEdition = new BookDto.Edition();
        duneEdition.setIsbn("9780441013593");
        duneEdition.setPublisherName("Ace Books");
        duneEdition.setEditionTitle("Dune - Deluxe Edition");
        duneEdition.setEditionNumber(1);
        duneEdition.setFormat("hardcover");
        duneEdition.setPublicationYear(1965);
        duneEdition.setLanguage("English");

        dune.setEditions(List.of(duneEdition));
        assertDoesNotThrow(() -> bookService.createBook(dune));

        // Book 2: 1984
        BookDto book1984 = new BookDto();
        book1984.setTitle("1984");
        book1984.setAuthors(List.of("George Orwell"));
        book1984.setGenres(List.of(new GenreDto("dystopian"), new GenreDto("political fiction"), new GenreDto("scifi")));
        book1984.setReadingStatus(ReadingStatusDto.READ);

        BookDto.Edition edition1984 = new BookDto.Edition();
        edition1984.setIsbn("9780451524935");
        edition1984.setPublisherName("Signet Classic");
        edition1984.setEditionTitle("1984 - Classic Edition");
        edition1984.setEditionNumber(1);
        edition1984.setFormat("paperback");
        edition1984.setPublicationYear(1949);
        edition1984.setLanguage("English");

        book1984.setEditions(List.of(edition1984));
        assertDoesNotThrow(() -> bookService.createBook(book1984));

        // Book 3: The Hobbit
        BookDto hobbit = new BookDto();
        hobbit.setTitle("The Hobbit");
        hobbit.setAuthors(List.of("J.R.R. Tolkien"));
        hobbit.setGenres(List.of(new GenreDto("fantasy")));
        hobbit.setReadingStatus(ReadingStatusDto.TO_READ);

        BookDto.Edition hobbitEdition = new BookDto.Edition();
        hobbitEdition.setIsbn("9780261102217");
        hobbitEdition.setPublisherName("HarperCollins");
        hobbitEdition.setEditionTitle("The Hobbit - Illustrated Edition");
        hobbitEdition.setEditionNumber(2);
        hobbitEdition.setFormat("hardcover");
        hobbitEdition.setPublicationYear(1937);
        hobbitEdition.setLanguage("English");

        hobbit.setEditions(List.of(hobbitEdition));
        assertDoesNotThrow(() -> bookService.createBook(hobbit));

        // Book 4: Clean Code
        BookDto cleanCode = new BookDto();
        cleanCode.setTitle("Clean Code");
        cleanCode.setAuthors(List.of("Robert C. Martin"));
        cleanCode.setGenres(List.of(new GenreDto("programming"), new GenreDto("software engineering")));
        cleanCode.setReadingStatus(ReadingStatusDto.READ);

        BookDto.Edition cleanCodeEdition = new BookDto.Edition();
        cleanCodeEdition.setIsbn("9780132350884");
        cleanCodeEdition.setPublisherName("Prentice Hall");
        cleanCodeEdition.setEditionTitle("Clean Code - A Handbook of Agile Software Craftsmanship");
        cleanCodeEdition.setEditionNumber(1);
        cleanCodeEdition.setFormat("paperback");
        cleanCodeEdition.setPublicationYear(2008);
        cleanCodeEdition.setLanguage("English");

        cleanCode.setEditions(List.of(cleanCodeEdition));
        assertDoesNotThrow(() -> bookService.createBook(cleanCode));

        return bookService;
    }

    @Test
    public void shouldFilterBooksByGenre() {
        BookService bookService = createBookServiceWithTestData();

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.addLeaf(BookFilterDto.Field.ANY_GENRE, BookFilterDto.FilterOperator.EQUALS, "scifi");

        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();

        assertEquals(2, books.size()); // Dune and 1984 have the "scifi" genre

        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780441013593")))); // Dune
        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780451524935")))); // 1984
    }

    @Test
    public void shouldFilterBooksByAuthor() {
        BookService bookService = createBookServiceWithTestData();

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.addLeaf(BookFilterDto.Field.ANY_AUTHOR_NAME, BookFilterDto.FilterOperator.EQUALS, "Frank Herbert");

        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();

        assertEquals(1, books.size()); // Only Dune by Frank Herbert

        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780441013593")))); // Dune
    }

    @Test
    public void shouldFilterBooksByTitle() {
        BookService bookService = createBookServiceWithTestData();

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.addLeaf(BookFilterDto.Field.TITLE, BookFilterDto.FilterOperator.CONTAINS, "hobbit");

        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();

        assertEquals(1, books.size()); // Only Dune matches the title

        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780261102217")))); // The Hobbit
    }

    @Test
    public void shouldFilterBooksByEditionIsbn() {
        BookService bookService = createBookServiceWithTestData();

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.addLeaf(BookFilterDto.Field.ANY_ISBN, BookFilterDto.FilterOperator.EQUALS, "9780132350884");

        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();

        assertEquals(1, books.size()); // Only Clean Code matches the ISBN

        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780132350884")))); // Clean Code
    }

    @Test
    public void shouldFilterBooksByPublicationYear() {
        BookService bookService = createBookServiceWithTestData();

        Locale.setDefault(Locale.ITALIAN);

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.addLeaf(BookFilterDto.Field.ANY_PUBLICATION_YEAR, BookFilterDto.FilterOperator.GREATER_THAN, 2000);

        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();

        assertEquals(1, books.size()); // Only Clean Code was published after 2000

        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780132350884")))); // Clean Code
    }

    @Test
    public void shouldFilterBooksByPublisherName() {
        BookService bookService = createBookServiceWithTestData();

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.addLeaf(BookFilterDto.Field.ANY_PUBLISHER_NAME, BookFilterDto.FilterOperator.EQUALS, "HarperCollins");

        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();

        assertEquals(1, books.size()); // only The Hobbit is published by HarperCollins

        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780261102217")))); // The Hobbit
    }

    @Test
    public void shouldFilterBooksByLanguage() {
        BookService bookService = createBookServiceWithTestData();

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.addLeaf(BookFilterDto.Field.ANY_LANGUAGE, BookFilterDto.FilterOperator.EQUALS, "english");

        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();

        assertEquals(4, books.size()); // All books are in English
    }

    @Test
    public void shouldFilterBooksByReadingStatus() {
        BookService bookService = createBookServiceWithTestData();

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.addLeaf(BookFilterDto.Field.READING_STATUS, BookFilterDto.FilterOperator.EQUALS, ReadingStatusDto.READ);

        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();

        assertEquals(2, books.size()); // 1984 and Clean Code are being read

        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780451524935")))); // 1984
        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780132350884")))); // Clean Code
    }
    
    @Test
    public void shouldFilterBooksWithComplexCriteria() {
        BookService bookService = createBookServiceWithTestData();
        
        // (genre = "scifi" AND publicationYear > 1950) OR (readingStatus = "READ" AND author = "George Orwell")

        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.setOperator(BookFilterDto.LogicalOperator.OR);

        BookFilterDto.CompositeFilterNode scifiGroup = BookFilterDto.createGroup(BookFilterDto.LogicalOperator.AND);
        scifiGroup.addCriterion(BookFilterDto.Field.ANY_GENRE, BookFilterDto.FilterOperator.EQUALS, "scifi");
        scifiGroup.addCriterion(BookFilterDto.Field.ANY_PUBLICATION_YEAR, BookFilterDto.FilterOperator.GREATER_THAN, 1950);
        
        BookFilterDto.CompositeFilterNode authorGroup = BookFilterDto.createGroup(BookFilterDto.LogicalOperator.AND);
        authorGroup.addCriterion(BookFilterDto.Field.READING_STATUS, BookFilterDto.FilterOperator.EQUALS, ReadingStatusDto.READ);
        authorGroup.addCriterion(BookFilterDto.Field.ANY_AUTHOR_NAME, BookFilterDto.FilterOperator.EQUALS, "George Orwell");
        
        bookFilterDto.addComposite(scifiGroup);
        bookFilterDto.addComposite(authorGroup);
        
        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();
        
        assertEquals(2, books.size()); // Should match Dune (scifi after 1950) and 1984 (by Orwell and READ)
        
        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780441013593")))); // Dune
        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780451524935")))); // 1984
    }
    
    @Test
    public void shouldFilterBooksWithNestedGroups() {
        BookService bookService = createBookServiceWithTestData();
        
        // title CONTAINS "e" AND (genre = "fantasy" OR (publicationYear < 1950 AND readingStatus = "READ"))
        
        BookFilterDto bookFilterDto = new BookFilterDto();
        bookFilterDto.setOperator(BookFilterDto.LogicalOperator.AND);
        
        bookFilterDto.addLeaf(BookFilterDto.Field.TITLE, BookFilterDto.FilterOperator.CONTAINS, "e");
        
        BookFilterDto.CompositeFilterNode nestedGroup = BookFilterDto.createGroup(BookFilterDto.LogicalOperator.OR);
        
        nestedGroup.addCriterion(BookFilterDto.Field.ANY_GENRE, BookFilterDto.FilterOperator.EQUALS, "fantasy");
        
        BookFilterDto.CompositeFilterNode innerGroup = BookFilterDto.createGroup(BookFilterDto.LogicalOperator.AND);
        innerGroup.addCriterion(BookFilterDto.Field.ANY_PUBLICATION_YEAR, BookFilterDto.FilterOperator.LESS_THAN_OR_EQUAL, 2008);
        innerGroup.addCriterion(BookFilterDto.Field.READING_STATUS, BookFilterDto.FilterOperator.EQUALS, ReadingStatusDto.READ);
        
        nestedGroup.addGroup(innerGroup);
        
        bookFilterDto.addComposite(nestedGroup);
        
        List<BookDto> books = bookService.findBooks(bookFilterDto).getElements();
        
        assertEquals(2, books.size());
        // should match The Hobbit (has 'e' in title and has fantasy genre)
        // and Clean Code (has 'e' in title and was published before 2008 and is READ)

        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780261102217")))); // The Hobbit
        assertTrue(books.stream().anyMatch(book -> book.getEditions().stream()
                .anyMatch(edition -> edition.getIsbn().equals("9780132350884")))); // Clean Code
    }


}
