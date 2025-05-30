package it.jiniux.gdlp.domain.exceptions;

import java.util.Set;

import it.jiniux.gdlp.domain.Isbn;
import lombok.Getter;

@Getter
public class IsbnAlreadyUsedByEditionException extends DomainException {
    private final Set<Isbn> isbn;

    public IsbnAlreadyUsedByEditionException(Set<Isbn> isbn) {
        super("ISBNs already used by editions: " + String.join(", ", isbn.stream().map(Isbn::getValue).sorted().toList()));
        this.isbn = Set.copyOf(isbn);
    }
}
