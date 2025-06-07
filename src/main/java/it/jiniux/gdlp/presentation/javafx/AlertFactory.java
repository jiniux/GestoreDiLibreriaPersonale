package it.jiniux.gdlp.presentation.javafx;

import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.scene.control.Alert;

import java.text.MessageFormat;

public class AlertFactory {
    private final Localization localization;

    public AlertFactory(Localization localization) {
        this.localization = localization;
    }

    private Alert createAlert(Alert.AlertType type) {
        return new Alert(type);
    }

    public Alert createAlert(AlertVariant variant, String... args) {
        Alert alert = createAlert(variant.getAlertType());

        alert.setContentText(localization.get(variant.getContentString(), (Object[]) args));
        alert.setTitle(localization.get(variant.getTitleString(), (Object[]) args));

        return alert;
    }
}
