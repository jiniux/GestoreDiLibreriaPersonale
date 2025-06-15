package it.jiniux.gdlp.core.domain.exceptions;

import it.jiniux.gdlp.core.domain.Edition;
import lombok.Getter;

@Getter
public class EditionAlreadyAddedException extends DomainException {
    private final String editionIsbn;
    private final String editionTitle;

    public EditionAlreadyAddedException(Edition edition) {
        super("Edition has already been added to this book");

        this.editionIsbn = edition.getIsbn().getValue();
        this.editionTitle = edition.getEditionTitle()
                .map(title -> title.getValue())
                .orElse("N/A");
    }
}
