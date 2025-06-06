package it.jiniux.gdlp.core.domain;

import it.jiniux.gdlp.utility.isbn.IsbnConverter;
import it.jiniux.gdlp.utility.isbn.IsbnValidator;
import lombok.Value;

@Value
public class Isbn {
    String value;

    public static Isbn createUnchecked(String value) {
        try {
            return new Isbn(value, false);
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Should not happen: " + e.getMessage(), e);
        }
    }

    public Isbn(String value) {
        this(value, true);
    }

    private Isbn(String value, boolean check) {
        if (value == null) {
            throw new IllegalArgumentException("ISBN cannot be null");
        }

        String digits = value.replaceAll("[^0-9X]", "");

        if (check) {
            if (digits.length() == 10) {
                if (!IsbnValidator.isValidIsbn10(digits)) {
                    throw new IllegalArgumentException("Invalid ISBN-10 format. Check digit may be incorrect");
                }
                digits = IsbnConverter.convertIsbn10to13(digits);
            } else if (digits.length() == 13) {
                if (!IsbnValidator.isValidIsbn13(digits)) {
                    throw new IllegalArgumentException("Invalid ISBN-13 format. Check digit may be incorrect");
                }
            } else {
                throw new IllegalArgumentException("ISBN must be either 10 or 13 digits long");
            }
        }

        this.value = digits;
    }
}