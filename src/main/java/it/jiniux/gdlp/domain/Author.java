package it.jiniux.gdlp.domain;

import it.jiniux.gdlp.domain.exceptions.AuthorNameEmptyException;
import lombok.Value;

@Value
public class Author {
    @Value
    public static class Name {
        String value;

        public Name(String value) throws AuthorNameEmptyException {
            if (value == null || value.isEmpty()) {
                throw new AuthorNameEmptyException();
            }
            this.value = value;
        }
    }

    Name name;

    public Author(Name name) {
        this.name = name;
    }
}
