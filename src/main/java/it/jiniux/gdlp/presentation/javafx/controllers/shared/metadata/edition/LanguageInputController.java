package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.common.suggestions.SuggestionsContextMenu;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class LanguageInputController implements Validable, Initializable {
    private final BookService bookService;

    @FXML private TextField languageField;

    public LanguageInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.bookService = serviceLocator.getBookService();
    }

    public String getLanguage() {
        if (languageField.getText() == null || languageField.getText().isBlank()) {
            return null;
        }

        return languageField.getText().trim();
    }
    
    public void setLanguage(String language) {
        languageField.setText(language);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        languageField.textProperty().addListener(new SuggestionsContextMenu(
            300, languageField, query -> bookService.findAllLanguagesContaining(query, 10)
        ));
    }
}
