package it.jiniux.gdlp.infrastructure.json;

import lombok.Data;

import java.util.List;

@Data
public class JsonBookData {
    private String id;
    private String title;
    private String description;
    private Integer rating;
    private JsonReadingStatusData readingStatus;
    private List<String> genres;
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
        private List<String> additionalAuthors;
    }
}
