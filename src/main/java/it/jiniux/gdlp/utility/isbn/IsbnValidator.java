package it.jiniux.gdlp.utility.isbn;

public final class IsbnValidator {
    private IsbnValidator() {}

    public static boolean isValidIsbn10(String isbn10) {
        if (!isbn10.matches("^[0-9]{9}[0-9X]$")) {
            return false;
        }

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

    public static boolean isValidIsbn13(String isbn13) {
        if (!isbn13.matches("^[0-9]{13}$")) {
            return false;
        }

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

}
