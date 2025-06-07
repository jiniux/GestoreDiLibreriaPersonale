package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import it.jiniux.gdlp.utility.isbn.IsbnValidator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

public final class IsbnInputController implements Initializable, Validable {
    private final Localization localization;
    
    @FXML private TextField isbnField;
    @FXML private Label isbnValidationLabel;
    
    @Getter
    private boolean valid = false;
    
    public IsbnInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }

    public String getIsbn() {
        return isbnField.getText().replace("-", "").trim();
    }
    
    public void setIsbn(String isbn) {
        isbnField.setText(isbn);
    }

    public void validate() {
        String isbn = isbnField.getText().replace("-", "").trim();
        
        if (isbn.isEmpty()) {
            showError(localization.get(LocalizationString.ISBN_FIELD_ERROR_EMPTY));
            valid = false;
            return;
        }

        if (isbn.matches("\\d{9}[\\dX]")) {
            if (!IsbnValidator.isValidIsbn10(isbn)) {
                showError(localization.get(LocalizationString.ISBN_INVALID));
                valid = false;
                return;
            }
        } else if (isbn.matches("\\d{13}")) {
            if (!IsbnValidator.isValidIsbn13(isbn)) {
                showError(localization.get(LocalizationString.ISBN_INVALID));
                valid = false;
                return;
            }
        } else {
            showError(localization.get(LocalizationString.ISBN_INVALID));
            valid = false;
            return;
        }
        
        valid = true;
        hideError();
    }

    private void hideError() {
        isbnValidationLabel.setVisible(false);
        isbnValidationLabel.setManaged(false);
    }

    private void showError(String message) {
        isbnValidationLabel.setText(message);
        isbnValidationLabel.setVisible(true);
        isbnValidationLabel.setManaged(true);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isbnValidationLabel.setVisible(false);
        isbnValidationLabel.setManaged(false);

        isbnField.textProperty().addListener((_, _, _) -> {
            validate();
        });
    }
}
