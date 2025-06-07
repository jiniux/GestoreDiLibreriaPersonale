package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.bookinfo;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class ReadingStatusInputController implements Initializable, Validable  {
    private final Localization localization;

    @FXML
    private Label labelText;
    
    @FXML
    private ComboBox<String> statusComboBox;
    
    @FXML
    private Label errorLabel;

    public ReadingStatusInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }

    public Optional<String> getReadingStatus() {
        return Optional.ofNullable(statusComboBox.getValue());
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
        labelText.setText(localization.get(LocalizationString.FIELD_READING_STATUS));
        hideError();
        
        // Populate the dropdown with reading status options
        statusComboBox.setItems(FXCollections.observableArrayList(
            localization.get(LocalizationString.STATUS_READ),
            localization.get(LocalizationString.STATUS_READING),
            localization.get(LocalizationString.STATUS_UNREAD),
            localization.get(LocalizationString.STATUS_ABANDONED)
        ));
        
        // Allow null selection (optional field)
        statusComboBox.setPromptText("Select reading status");
    }
}
