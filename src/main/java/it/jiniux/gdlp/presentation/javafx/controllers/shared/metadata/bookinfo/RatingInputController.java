package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.bookinfo;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
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
    
    @FXML
    private Button clearRatingButton;

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
    
    @FXML
    public void clearRating() {
        ratingValueLabel.setText(null);
        isEnabled = false;
        clearRatingButton.setDisable(true);
    }

    public void setRating(Integer rating) {
        if (rating == null || rating < 0 || rating > 5) {
            ratingSlider.setValue(3);
            ratingValueLabel.setText(null);
            isEnabled = false;
            clearRatingButton.setDisable(true);
        } else {
            ratingSlider.setValue(rating);
            ratingValueLabel.setText(String.valueOf(rating));
            isEnabled = true;
            clearRatingButton.setDisable(false);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        isEnabled = false;
        clearRatingButton.setDisable(true);
        ratingValueLabel.setText(null);

        ratingSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            int intValue = newValue.intValue();
            ratingValueLabel.setText(String.valueOf(intValue));
            isEnabled = true;
            clearRatingButton.setDisable(false);
        });
    }
}
