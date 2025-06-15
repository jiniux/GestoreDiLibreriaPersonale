package it.jiniux.gdlp.core.domain.exceptions;

import java.util.Set;
import java.util.stream.Collectors;

import it.jiniux.gdlp.core.domain.Isbn;
import lombok.Getter;

@Getter
public class IsbnAlreadyUsedByEditionException extends DomainException {
    private final Set<String> isbns;

    public IsbnAlreadyUsedByEditionException(Set<Isbn> isbn) {
        super("ISBNs already used by editions: " + String.join(", ", isbn.stream().map(Isbn::getValue).sorted().toList()));
        this.isbns = isbn.stream()
                .map(i -> i.getValue())
                .collect(Collectors.toSet());
    }
}
