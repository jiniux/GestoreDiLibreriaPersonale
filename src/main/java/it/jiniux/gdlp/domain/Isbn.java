package it.jiniux.gdlp.domain;

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
                if (!isValidIsbn10(digits)) {
                    throw new IllegalArgumentException("Invalid ISBN-10 format. Check digit may be incorrect");
                }
                digits = convertIsbn10to13(digits);
            } else if (digits.length() == 13) {
                if (!isValidIsbn13(digits)) {
                    throw new IllegalArgumentException("Invalid ISBN-13 format. Check digit may be incorrect");
                }
            } else {
                throw new IllegalArgumentException("ISBN must be either 10 or 13 digits long");
            }
        }

        this.value = digits;
    }

    private static boolean isValidIsbn10(String isbn10) {
        for (int i = 0; i < 9; i++) {
            if (!Character.isDigit(isbn10.charAt(i))) {
                return false;
            }
        }

        int sum = 0;
        for (int i = 0; i < 9; i++) {
            sum += (isbn10.charAt(i) - '0') * (10 - i);
        }

        char lastChar = isbn10.charAt(9);
        if (lastChar == 'X' || lastChar == 'x') {
            sum += 10;
        } else if (Character.isDigit(lastChar)) {
            sum += lastChar - '0';
        } else {
            return false;
        }

        return sum % 11 == 0;
    }

    private static boolean isValidIsbn13(String isbn13) {
        for (int i = 0; i < isbn13.length(); i++) {
            if (!Character.isDigit(isbn13.charAt(i))) {
                return false;
            }
        }

        int sum = calculateSum(isbn13);

        int checkDigit = (10 - (sum % 10)) % 10;
        return checkDigit == (isbn13.charAt(12) - '0');
    }

    private static int calculateSum(String isbn13) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn13.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return sum;
    }

    private static String convertIsbn10to13(String isbn10) {
        StringBuilder sb = new StringBuilder("978");

        for (int i = 0; i < 9; i++) {
            sb.append(isbn10.charAt(i));
        }

        String isbn13body = sb.toString();

        int sum = calculateSum(isbn13body);

        int checkDigit = (10 - (sum % 10)) % 10;
        return isbn13body + checkDigit;
    }
}