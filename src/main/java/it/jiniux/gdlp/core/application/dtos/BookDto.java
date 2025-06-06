package it.jiniux.gdlp.core.application.dtos;

import lombok.Data;

import java.util.List;

@Data
public class BookDto {
    private String id;
    private String title;
    private String description;
    private Integer rating;
    private ReadingStatusDto readingStatus;
    private List<GenreDto> genres;
    private List<String> authors;
    private List<Edition> editions;
    
    @Data
    public static class Edition {
        private String isbn;
        private String publisherName;
        private String editionTitle;
        private Integer editionNumber;
        private String format;
        private String language;
        private Integer publicationYear;
        private byte[] coverImage;
        private List<String> additionalAuthors;
    }
}
