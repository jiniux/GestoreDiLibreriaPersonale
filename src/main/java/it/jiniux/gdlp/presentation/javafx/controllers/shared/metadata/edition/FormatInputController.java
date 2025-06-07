package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

public class FormatInputController implements Initializable, Validable {
    private final Localization localization;
    
    @FXML private TextField formatField;
    
    public FormatInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }
    
    public String getFormat() {
        if (formatField.getText().isBlank()) {
            return null;
        }

        return formatField.getText().trim();
    }
    
    public void setFormat(String format) {
        formatField.setText(format);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }
}
