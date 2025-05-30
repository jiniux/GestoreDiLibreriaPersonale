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
import it.jiniux.gdlp.domain.Author;
import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.Book.Title;
import it.jiniux.gdlp.domain.BookRepository;
import it.jiniux.gdlp.domain.Isbn;
import it.jiniux.gdlp.domain.exceptions.DomainException;

public class BookServiceTest {
    public BookRepository createBookRepository() {
        return new InMemoryBookRepository();
    }

    @Test
    public void shouldAddSimpleBook() throws DomainException {
        BookRepository bookRepository = createBookRepository();
        BookService bookService = new BookService(bookRepository);
        
        BookDto bookDto = new BookDto();
        bookDto.setTitle("Il nome della rosa");
        
        bookDto.setAuthors(Arrays.asList("Umberto Eco"));
        
        GenreDto genreDto = new GenreDto();
        genreDto.setName("Mystery");
        bookDto.setGenres(Arrays.asList(genreDto));
        
        BookDto.Edition editionDto = new BookDto.Edition();
        editionDto.setIsbn("9788845292613");
        
        editionDto.setPublisherName("Bompiani");
        
        bookDto.setEditions(Arrays.asList(editionDto));
        
        bookService.createBook(bookDto);
        
        Book savedBook = assertDoesNotThrow(() -> bookRepository.findBookByIsbn(new Isbn("9788845292613")).orElseThrow());

        assertEquals("Il nome della rosa", savedBook.getTitle().getValue());
        assertEquals(1, savedBook.getAuthors().size());
        assertEquals(1, savedBook.getGenres().size());
        assertEquals(1, savedBook.getEditions().size());
        assertEquals("9788845292613", savedBook.getEditions().iterator().next().getIsbn().getValue());
    }
    
    private static class InMemoryBookRepository implements BookRepository {
        private final List<Book> books = new ArrayList<>();
        private final Map<Isbn, Book> isbnIndex = new HashMap<>();
        
        @Override
        public void saveBook(Book book) {
            Optional<Book> existingBook = findBookByTitleAndAuthors(book.getTitle(), book.getAuthors());
            existingBook.ifPresent(books::remove);

            for (Isbn isbn : book.getIsbns()) {
                isbnIndex.put(isbn, book);
            }
            
            books.add(book);
        }

        @Override
        public void deleteBook(Book book) {
            books.removeIf(b -> 
                b.getTitle().equals(book.getTitle()) && 
                b.getAuthors().equals(book.getAuthors()));

            for (Isbn isbn : book.getIsbns()) {
                isbnIndex.remove(isbn);
            }
        }

        @Override
        public Set<Isbn> findAlreadyExistingIsbns(List<Isbn> isbns) {
            Set<Isbn> existingIsbns = new HashSet<>();
            
            for (Book book : books) {
                Set<Isbn> bookIsbns = book.getIsbns();
                for (Isbn isbn : isbns) {
                    if (bookIsbns.contains(isbn)) {
                        existingIsbns.add(isbn);
                    }
                }
            }
            
            return existingIsbns;
        }

        @Override
        public Optional<Book> findBookByTitleAndAuthors(Title title, Set<Author> authors) {
            return books.stream()
                .filter(book -> book.getTitle().equals(title) && book.getAuthors().equals(authors))
                .findFirst();
        }

        @Override
        public Optional<Book> findBookByIsbn(Isbn isbn) {
            return isbnIndex.containsKey(isbn) ? 
                Optional.of(isbnIndex.get(isbn)) : 
                books.stream().filter(book -> book.getIsbns().contains(isbn)).findFirst();
        }
    }
}
