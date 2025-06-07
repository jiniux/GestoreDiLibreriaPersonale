package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import lombok.Getter;

import java.net.URL;
import java.time.Year;
import java.util.ResourceBundle;

public class PublicationYearInputController implements Initializable, Validable {
    private final Localization localization;
    
    @FXML private TextField publicationYearField;
    @FXML private Label errorLabel;

    @Getter
    private boolean valid = true;
    
    public PublicationYearInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }
    
    public Integer getPublicationYear() {
        String yearText = publicationYearField.getText().trim();
        if (yearText.isEmpty()) {
            return null;
        }
        
        try {
            return Integer.parseInt(yearText);
        } catch (NumberFormatException e) {
            return null;
        }
    }
    
    public void setPublicationYear(Integer year) {
        if (year != null) {
            publicationYearField.setText(year.toString());
        } else {
            publicationYearField.clear();
        }
    }

    public void validate() {
        String yearText = publicationYearField.getText().trim();
        
        if (yearText.isEmpty()) {
            hideError();
            valid = true;
            return;
        }
        
        try {
            int year = Integer.parseInt(yearText);
            int currentYear = Year.now().getValue();
            
            if (year < 0 || year > currentYear + 5) { // Allow up to 5 years in the future for pre-publications
                showError("Publication year must be between 0 and " + (currentYear + 5));
                valid = false;
            } else {
                hideError();
                valid = true;
            }
        } catch (NumberFormatException e) {
            showError("Publication year must be a valid number");
            valid = false;
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
        
        publicationYearField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                publicationYearField.setText(newValue.replaceAll("[^\\d]", ""));
            }
            validate();
        });
    }
}
