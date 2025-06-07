package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.bookinfo;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.common.suggestions.SuggestionTextFieldFactory;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.MultipleTextInputController;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class GenresInputController implements Initializable, Validable {
    private final BookService bookService;
    private final Localization localization;

    @FXML
    MultipleTextInputController multipleTextInputController;
    
    @FXML
    private Label errorLabel;

    public GenresInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.bookService = serviceLocator.getBookService();
        this.localization = serviceLocator.getLocalization();
    }

    public List<String> getGenres() {
        return multipleTextInputController.getEntries();
    }

    @Getter
    private boolean valid = true;

    @Setter(AccessLevel.PROTECTED)
    private String error;

    private void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }

    private void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    public void validate() {
        calculateValidation();

        if (valid) {
            hideError();
        } else {
            assert error != null : "error should not be null if form is invalid";
            showError(error);
        }
    }

    public void calculateValidation() {
        List<String> genres = multipleTextInputController.getEntries().stream().toList();

        boolean genresBlank = genres.stream()
                .anyMatch(author -> author == null || author.isBlank());

        Set<String> uniqueGenres = genres.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        boolean duplicateAuthors = uniqueGenres.size() < genres.size();

        valid = true;

        if (duplicateAuthors) {
            setError(localization.get(LocalizationString.GENRE_FIELD_ERROR_DUPLICATE));
            valid = false;
        } else if (genresBlank) {
            setError(localization.get(LocalizationString.GENRE_FIELD_ERROR_EMPTY));
            valid = false;
        }
    }

    private class TextFieldFactory extends  SuggestionTextFieldFactory {
        public TextFieldFactory() {
            super(text -> bookService.findAllGenresContaining(text, 10), 300);
        }

        @Override
        public TextField createTextField() {
            TextField textField = super.createTextField();

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                textField.setText(newValue.toLowerCase());
                validate();
            });

            return textField;
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        multipleTextInputController.setTextFieldFactory(new TextFieldFactory());

        multipleTextInputController.setPromptText(localization.get(LocalizationString.GENRE_FIELD_PROMPT));
        multipleTextInputController.setLabelText(localization.get(LocalizationString.FIELD_GENRES));
        multipleTextInputController.setMinimumRows(1);
        multipleTextInputController.attach((event) -> {
            if (event instanceof MultipleTextInputController.Event.RemovedTextField) {
                validate();
            }
        });

        hideError();
    }
}
