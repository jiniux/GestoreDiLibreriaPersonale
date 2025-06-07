package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.bookinfo;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;

import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class RatingInputController implements Initializable, Validable {
    private final Localization localization;

    @FXML
    private Slider ratingSlider;

    @FXML
    private Label ratingValueLabel;

    private boolean isEnabled = false;

    public RatingInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }

    public Optional<Integer> getRating() {
        if (isEnabled) {
            return Optional.of((int) ratingSlider.getValue());
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isEnabled = false;

        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int intValue = newValue.intValue();
            ratingValueLabel.setText(String.valueOf(intValue));
            isEnabled = true;
        });
    }
}
