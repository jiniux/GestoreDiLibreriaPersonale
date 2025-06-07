package it.jiniux.gdlp.core.domain;

import lombok.Value;

@Value
public class Author {
    @Value
    public static class Name {
        String value;

        public Name(String value) {
            if (value == null || value.isBlank()) {
                throw new IllegalArgumentException("Author name cannot be null or empty");
            }
            this.value = value.trim();
        }
    }

    Name name;

    public Author(Name name) {
        this.name = name;
    }
}
