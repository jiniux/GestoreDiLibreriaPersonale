package it.jiniux.gdlp.core.domain;

import it.jiniux.gdlp.core.domain.Isbn;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class IsbnTest {
    @Test
    void validIsbn10IsAcceptedAndConvertedTo13() {
        // ISBN-10: 0-19-853453-1 -> ISBN-13: 9788804496018
        Isbn isbn = assertDoesNotThrow(() -> new Isbn("0198534531"));
        assertEquals("9780198534532", isbn.getValue());
    }

    @Test
    void validIsbn13IsAccepted() {
        Isbn isbn = assertDoesNotThrow(() -> new Isbn("9780399590504"));
        assertEquals("9780399590504", isbn.getValue());
    }

    @Test
    void validIsbn10WithHyphensIsAccepted() {
        Isbn isbn = assertDoesNotThrow(() -> new Isbn("1-4434-3497-3"));
        assertEquals("9781443434973", isbn.getValue());
    }

    @Test
    void validIsbn13WithSpacesIsAccepted() {
        Isbn isbn = assertDoesNotThrow(() -> new Isbn("978 15   24   7  6   3    138"));
        assertEquals("9781524763138", isbn.getValue());
    }

    @Test
    void invalidIsbn10Throws() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("8804496012"));
    }

    @Test
    void invalidIsbn13Throws() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("9788804496017"));
    }

    @Test
    void isbnWithInvalidLengthThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn("123456789"));
        assertThrows(IllegalArgumentException.class, () -> new Isbn("123456789012"));
    }

    @Test
    void nullIsbnThrows() {
        assertThrows(IllegalArgumentException.class, () -> new Isbn(null));
    }

    @Test
    void isbn10WithXCheckDigitIsAccepted() {
        // ISBN-10: 0306406152, ISBN-10: 030640615X (X as check digit)
        Isbn isbn = assertDoesNotThrow(() -> new Isbn("145162705X"));
        assertEquals("9781451627053", isbn.getValue());

        Isbn isbnX = assertDoesNotThrow(() -> new Isbn("155404295X"));
        assertEquals("9781554042951", isbnX.getValue());
    }
}
