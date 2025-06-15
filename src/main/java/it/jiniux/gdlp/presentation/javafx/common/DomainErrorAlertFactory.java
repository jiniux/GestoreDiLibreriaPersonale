package it.jiniux.gdlp.presentation.javafx.common;

import it.jiniux.gdlp.core.domain.exceptions.*;
import it.jiniux.gdlp.presentation.javafx.AlertFactory;
import it.jiniux.gdlp.presentation.javafx.AlertVariant;
import javafx.scene.control.Alert;
import lombok.RequiredArgsConstructor;

import java.util.stream.Collectors;

@RequiredArgsConstructor
public class DomainErrorAlertFactory {
    private final AlertFactory alertFactory;
    
    public Alert createAlertForException(DomainException exception) {
        if (exception instanceof NoGenreAddedException) {
            return alertFactory.createAlert(AlertVariant.NO_GENRE_ADDED);
        } else if (exception instanceof NoEditionAddedException) {
            return alertFactory.createAlert(AlertVariant.NO_EDITION_ADDED);
        } else if (exception instanceof NoAuthorAddedException) {
            return alertFactory.createAlert(AlertVariant.NO_AUTHOR_ADDED);
        } else if (exception instanceof IsbnAlreadyUsedByEditionException ex) {
            String isbns = ex.getIsbns().stream().collect(Collectors.joining(", "));
            return alertFactory.createAlert(AlertVariant.ISBN_ALREADY_USED_BY_EDITION, isbns);
        } else if (exception instanceof EditionAlreadyAddedException ex) {
            String editionTitle = ex.getEditionIsbn();
            String editionIsbn = ex.getEditionTitle();
            return alertFactory.createAlert(AlertVariant.EDITION_ALREADY_ADDED, editionTitle, editionIsbn);
        } else if (exception instanceof BookWithSameTitleAndAuthorsAlreadyExists ex) {
             String bookTitle = ex.getBookTitle();
            return alertFactory.createAlert(AlertVariant.BOOK_WITH_SAME_TITLE_AUTHORS_EXISTS, bookTitle);
        } else if (exception instanceof BookDoesNotExistException ex) {
            String message = ex.getMessage();
            String idPart = message.substring("Book with ID ".length(), message.lastIndexOf(" does not exist."));
            return alertFactory.createAlert(AlertVariant.BOOK_DOES_NOT_EXIST, idPart);
        } else if (exception instanceof BookByIsbnDoesNotExistException ex) {
            String isbn = ex.getIsbn();
            return alertFactory.createAlert(AlertVariant.BOOK_BY_ISBN_DOES_NOT_EXIST, isbn);
        } else if (exception instanceof AuthorAlreadyAddedException ex) {
            String authorName = ex.getAuthorName();
            return alertFactory.createAlert(AlertVariant.AUTHOR_ALREADY_ADDED, authorName);
        } else {
            return alertFactory.createAlert(AlertVariant.GENERIC_DOMAIN_ERROR, exception.getMessage());
        }
    }
}
