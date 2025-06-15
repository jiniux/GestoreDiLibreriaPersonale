package it.jiniux.gdlp.presentation.javafx.errors;

import it.jiniux.gdlp.presentation.javafx.AlertFactory;
import it.jiniux.gdlp.presentation.javafx.AlertVariant;

public class GenericErrorHandler extends BaseErrorHandler {
    private final AlertFactory alertFactory;

    public GenericErrorHandler(AlertFactory alertFactory) {
        this.alertFactory = alertFactory;
    }

    @Override
    public void handle(Throwable e) {
        if (e != null) {
            alertFactory.createAlert(AlertVariant.GENERIC_ERROR, e.getMessage()).showAndWait();
        }

        super.handle(e);
    }
}
