package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class EditionDetailsInputController implements Initializable, Validable {
    private final Localization localization;
    
    @FXML private TextField editionNumberField;
    @FXML private TextField editionTitleField;
    
    public EditionDetailsInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }
    
    public Integer getEditionNumber() {
        if (editionNumberField.getText() == null || editionNumberField.getText().isBlank()) {
            return null;
        }
        return Integer.parseInt(editionNumberField.getText().trim());
    }
    
    public String getEditionTitle() {
        if (editionTitleField.getText() == null || editionTitleField.getText().isBlank()) {
            return null;
        }

        return editionTitleField.getText().trim();
    }
    
    public void setEditionNumber(int editionNumber) {
        editionNumberField.setText(Integer.toString(editionNumber));
    }
    
    public void setEditionTitle(String editionTitle) {
        editionTitleField.setText(editionTitle);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editionNumberField.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                editionNumberField.setText(newValue.replaceAll("\\D", ""));
            }
            validate();
        });
    }
}
