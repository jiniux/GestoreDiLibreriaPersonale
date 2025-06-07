package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.common.suggestions.SuggestionsContextMenu;
import it.jiniux.gdlp.presentation.javafx.common.suggestions.SuggestionsRetriever;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.Set;

public class PublisherInputController implements Initializable, Validable {
    private final BookService bookService;
    private final Localization localization;
    
    @FXML private TextField publisherField;
    @FXML private Label errorLabel;
    
    @Getter
    private boolean valid = false;
    
    public PublisherInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.bookService = serviceLocator.getBookService();
        this.localization = serviceLocator.getLocalization();
    }

    public String getPublisher() {
        return publisherField.getText().trim();
    }
    
    public void setPublisher(String publisher) {
        publisherField.setText(publisher);
    }
    
    public void validate() {
        String publisher = getPublisher();
        
        if (publisher.isEmpty()) {
            showError(localization.get(LocalizationString.PUBLISHER_FIELD_ERROR_EMPTY));
            valid = false;
        } else {
            hideError();
            valid = true;
        }
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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        hideError();
        
        publisherField.textProperty().addListener((observable, oldValue, newValue) -> {
            validate();
        });

        publisherField.textProperty().addListener(new SuggestionsContextMenu(
                300,
                publisherField,
                query -> bookService.findAllPublishersContaining(query, 10)));
    }
}
