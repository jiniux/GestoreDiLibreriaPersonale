package it.jiniux.gdlp.domain;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import it.jiniux.gdlp.domain.exceptions.DomainException;
import it.jiniux.gdlp.domain.exceptions.EditionTitleEmptyException;
import it.jiniux.gdlp.domain.exceptions.FormatEmptyException;
import it.jiniux.gdlp.domain.exceptions.InvalidEditionNumberException;
import it.jiniux.gdlp.domain.exceptions.LanguageEmptyException;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.Value;

@EqualsAndHashCode(of = "isbn")
@Getter
public class Edition implements Cloneable {
    
    @Value
    public static class EditionTitle {
        String value;
        
        public EditionTitle(String value) throws EditionTitleEmptyException {
            if (value == null || value.isEmpty()) {
                throw new EditionTitleEmptyException();
            }
            this.value = value;
        }
    }
    
    @Value
    public static class EditionNumber {
        int value;
        
        public EditionNumber(int value) throws InvalidEditionNumberException {
            if (value <= 0) {
                throw new InvalidEditionNumberException();
            }
            this.value = value;
        }
    }
    
    @Value
    public static class Format {
        String value;
        
        public Format(String value) throws FormatEmptyException {
            if (value == null || value.isEmpty()) {
                throw new FormatEmptyException();
            }
            this.value = value;
        }
    }
    
    @Value
    public static class Language {
        String value;
        
        public Language(String value) throws LanguageEmptyException {
            if (value == null || value.isEmpty()) {
                throw new it.jiniux.gdlp.domain.exceptions.LanguageEmptyException();
            }
            this.value = value;
        }
    }

    private final Isbn isbn;
    private final Publisher publisher;
    private final Set<Author> additionalAuthors;
    
    @Setter
    private EditionTitle editionTitle;
    
    @Setter
    private EditionNumber editionNumber;
    
    @Setter
    private Format format;
    
    @Setter
    private Language language;
    
    @Setter
    private Integer publicationYear;
    
    @Setter
    private byte[] coverImage;

    public Edition(Isbn isbn, Publisher publisher) throws DomainException {
        this.isbn = isbn;
        this.publisher = publisher;
        this.additionalAuthors = new HashSet<>();
    }
    
    public Optional<EditionTitle> getEditionTitle() {
        return Optional.ofNullable(editionTitle);
    }
    
    public Optional<EditionNumber> getEditionNumber() {
        return Optional.ofNullable(editionNumber);
    }
    
    public Optional<Format> getFormat() {
        return Optional.ofNullable(format);
    }
    
    public Optional<Language> getLanguage() {
        return Optional.ofNullable(language);
    }
    
    public Optional<Integer> getPublicationYear() {
        return Optional.ofNullable(publicationYear);
    }
    
    public Optional<byte[]> getCoverImage() {
        return Optional.ofNullable(coverImage);
    }
    
    public void addAdditionalAuthor(Author author) {
        if (author == null) {
            throw new IllegalArgumentException("Author cannot be null");
        }
        additionalAuthors.add(author);
    }

    @Override
    public Edition clone() {
        try {
            Edition clone = (Edition) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError("Should not happen because we implement Cloneable", e);
        }
    }
}
