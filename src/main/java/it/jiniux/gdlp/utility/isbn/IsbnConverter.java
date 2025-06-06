package it.jiniux.gdlp.utility.isbn;

public final class IsbnConverter {
    private IsbnConverter() {}

    private static int calculateSum(String isbn13) {
        int sum = 0;
        for (int i = 0; i < 12; i++) {
            int digit = isbn13.charAt(i) - '0';
            sum += (i % 2 == 0) ? digit : digit * 3;
        }
        return sum;
    }

    public static String convertIsbn10to13(String isbn10) {
        if (!IsbnValidator.isValidIsbn10(isbn10)) {
            throw new IllegalArgumentException("Invalid ISBN-10 format. Check digit may be incorrect");
        }

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

