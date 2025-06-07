package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.suggestions.SuggestionTextFieldFactory;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.MultipleTextInputController;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.AuthorsInputController;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import it.jiniux.gdlp.utility.observer.Observer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AdditionalAuthorsInputController extends AuthorsInputController {
    @Override
    public void calculateValidation() {
        List<String> authors = getAuthors();
        Set<String> uniqueAuthors = new HashSet<>(authors);

        valid = true;

        if (authors.isEmpty()) {
            return;
        }

        if (authors.stream().anyMatch(String::isBlank)) {
            setError(localization.get(LocalizationString.AUTHOR_FIELD_ERROR_EMPTY));
            valid = false;
        } else if (uniqueAuthors.size() < authors.size()) {
            setError(localization.get(LocalizationString.AUTHOR_FIELD_ERROR_DUPLICATE));
            valid = false;
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        multipleTextInputController.setLabelText(localization.get(LocalizationString.FIELD_ADDITIONAL_AUTHORS));
        multipleTextInputController.setMinimumRows(0);
    }
}
