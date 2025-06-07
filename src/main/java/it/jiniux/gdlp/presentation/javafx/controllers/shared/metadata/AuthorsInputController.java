package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.common.suggestions.SuggestionTextFieldFactory;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.MultipleTextInputController;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import it.jiniux.gdlp.utility.observer.Observer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.AccessLevel;
import lombok.Setter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

public class AuthorsInputController implements Initializable, Observer<MultipleTextInputController.Event>, Validable {
    private final BookService bookService;
    protected final Localization localization;

    @FXML
    protected MultipleTextInputController multipleTextInputController;
    
    @FXML
    private Label errorLabel;

    public AuthorsInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.bookService = serviceLocator.getBookService();
        this.localization = serviceLocator.getLocalization();
    }

    public List<String> getAuthors() {
        return multipleTextInputController.getEntries().stream().map(String::trim).toList();
    }

    protected boolean valid = true;

    public boolean isValid() {
        calculateValidation();

        return valid;
    }

    private void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
    
    private void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    @Setter(AccessLevel.PROTECTED)
    private String error;

    public final void validate() {
        calculateValidation();

        if (valid) {
            hideError();
        } else {
            assert error != null : "error should not be null if form is invalid";
            showError(error);
        }
    }

    public void calculateValidation() {
        List<String> authors = multipleTextInputController.getEntries().stream().toList();
        assert !authors.isEmpty() : "authors should never be empty";

        boolean anyAuthorBlank = authors.stream()
                .anyMatch(author -> author == null || author.isBlank());

        Set<String> uniqueAuthors = authors.stream()
                .map(String::toLowerCase)
                .collect(Collectors.toSet());

        boolean duplicateAuthors = uniqueAuthors.size() < authors.size();

        valid = true;

        if (duplicateAuthors) {
            setError(localization.get(LocalizationString.AUTHOR_FIELD_ERROR_DUPLICATE));
            valid = false;
        } else if (anyAuthorBlank) {
            setError(localization.get(LocalizationString.AUTHOR_FIELD_ERROR_EMPTY));
            valid = false;
        }
    }

    @Override
    public void update(MultipleTextInputController.Event event) {
        if (event instanceof MultipleTextInputController.Event.RemovedTextField) {
            validate();
        }
    }

    protected class TextFieldFactory extends  SuggestionTextFieldFactory {
        public TextFieldFactory() {
            super(text -> bookService.findAllAuthorsContaining(text, 10), 300);
        }

        @Override
        public TextField createTextField() {
            TextField textField = super.createTextField();

            textField.textProperty().addListener((observable, oldValue, newValue) -> {
                validate();
            });

            return textField;
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        multipleTextInputController.setTextFieldFactory(new TextFieldFactory());

        multipleTextInputController.setPromptText(localization.get(LocalizationString.AUTHOR_FIELD_PROMPT));
        multipleTextInputController.setLabelText(localization.get(LocalizationString.FIELD_AUTHORS));
        multipleTextInputController.setMinimumRows(1);
        multipleTextInputController.attach((event) -> {
            if (event instanceof MultipleTextInputController.Event.RemovedTextField) {
                validate();
            }
        });
    }
}
