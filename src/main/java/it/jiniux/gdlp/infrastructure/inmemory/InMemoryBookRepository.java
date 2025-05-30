package it.jiniux.gdlp.infrastructure.inmemory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import it.jiniux.gdlp.domain.Author;
import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.BookRepository;
import it.jiniux.gdlp.domain.Book.Title;
import it.jiniux.gdlp.domain.Isbn;

public class InMemoryBookRepository implements BookRepository {
    private final List<Book> books = new ArrayList<>();
    private final Map<Isbn, Book> isbnIndex = new HashMap<>();
    private final Map<Book.Id, Book> bookIdIndex = new HashMap<>();
    
    @Override
    public void saveBook(Book book) {
        Optional<Book> existingBook = findBookByTitleAndAuthors(book.getTitle(), book.getAuthors());
        existingBook.ifPresent(books::remove);

        for (Isbn isbn : book.getIsbns()) {
            isbnIndex.put(isbn, book);
        }
        
        books.add(book);
        bookIdIndex.put(book.getId(), book);
    }

    @Override
    public void deleteBook(Book book) {
        books.removeIf(b -> 
            b.getTitle().equals(book.getTitle()) && 
            b.getAuthors().equals(book.getAuthors()));

        for (Isbn isbn : book.getIsbns()) {
            isbnIndex.remove(isbn);
        }

        bookIdIndex.remove(book.getId());
    }

    @Override
    public void patchBook(Book book) {
        Optional<Book> existingBook = findBookById(book.getId());
        existingBook.ifPresent(this::deleteBook);

        saveBook(book);
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

    @Override
    public Optional<Book> findBookById(Book.Id id) {
        return Optional.ofNullable(bookIdIndex.get(id));
    }
}
