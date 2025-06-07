package it.jiniux.gdlp.presentation.javafx.controllers;

import it.jiniux.gdlp.presentation.javafx.FXMLFactory;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class DashboardController {
    private final FXMLFactory FXMLFactory;
    private final Localization localization;
    private final ErrorHandler errorHandler;

    public DashboardController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        this.FXMLFactory = serviceLocator.getFXMLFactory();
        this.localization = serviceLocator.getLocalization();
        this.errorHandler = serviceLocator.getErrorHandler();
    }

    private Stage createNewBookStage() throws IOException {
        Stage stage = new Stage();
        stage.setTitle(localization.get(LocalizationString.ADD_NEW_BOOK_WINDOW_TITLE));
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(new Scene(FXMLFactory.createNewBookScene().load()));

        return stage;
    }

    @FXML
    private void onNewBookClick(ActionEvent event) {
        try {
            createNewBookStage().showAndWait();
        } catch (IOException e) {
            errorHandler.handle(e);
        }
    }
}
