package it.jiniux.gdlp.domain;

import lombok.Value;

@Value
public class Author {
    @Value
    public static class Name {
        String value;

        public Name(String value) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("Author name cannot be null or empty");
            }
            this.value = value;
        }
    }

    Name name;

    public Author(Name name) {
        this.name = name;
    }
}
