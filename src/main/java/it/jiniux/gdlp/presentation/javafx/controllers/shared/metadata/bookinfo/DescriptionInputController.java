package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.bookinfo;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextArea;

import java.net.URL;
import java.util.ResourceBundle;

public class DescriptionInputController implements Initializable, Validable {
    private final Localization localization;

    @FXML
    private TextArea descriptionArea;

    public DescriptionInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }

    public void setDescription(String description) {
        if (description == null || description.isBlank()) {
            descriptionArea.clear();
            return;
        }

        descriptionArea.setText(description.trim());
    }

    public String getDescription() {
        if (descriptionArea.getText().isBlank()) {
            return null;
        }

        return descriptionArea.getText().trim();
    }

    public boolean isValid() {
        return true;
    }

    public void validate() {
        // no validation needed
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
