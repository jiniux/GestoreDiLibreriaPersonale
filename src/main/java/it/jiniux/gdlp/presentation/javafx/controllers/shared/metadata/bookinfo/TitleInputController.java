package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.bookinfo;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.net.URL;
import java.util.ResourceBundle;

public class TitleInputController implements Initializable, Validable {
    private final Localization localization;
    @Getter
    private boolean valid = true;

    @FXML
    private TextField titleField;
    
    @FXML
    private Label errorLabel;

    public TitleInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }

    public void setTitle(String title) {
        titleField.setText(title);
    }

    public String getTitle() {
        return titleField.getText().trim();
    }

    public void validate() {
        String title = getTitle();
        
        if (title.isEmpty()) {
            showError(localization.get(LocalizationString.TITLE_FIELD_ERROR_EMPTY));
            valid = false;
        } else {
            hideError();
            valid = true;
        }
    }
    
    public void showError(String errorMessage) {
        errorLabel.setText(errorMessage);
        errorLabel.setVisible(true);
        errorLabel.setManaged(true);
    }
    
    public void hideError() {
        errorLabel.setVisible(false);
        errorLabel.setManaged(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        titleField.textProperty().addListener((observable, oldValue, newValue) -> {
            validate();
        });
        
        hideError();
    }
}
