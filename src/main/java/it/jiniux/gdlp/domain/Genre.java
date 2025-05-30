package it.jiniux.gdlp.domain;

import lombok.Value;

@Value
public class Genre {
    @Value
    public static class Name {
        String value;

        public Name(String value) {
            if (value == null || value.isEmpty()) {
                throw new IllegalArgumentException("Genre name cannot be empty");
            }
            this.value = value;
        }
    }

    Name name;
}
