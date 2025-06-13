package it.jiniux.gdlp.presentation.javafx.controllers;

import it.jiniux.gdlp.presentation.javafx.FXMLFactory;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Mediator;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DashboardController implements Initializable, Mediator<ActionEvent> {
    private final FXMLFactory FXMLFactory;
    private final Localization localization;
    private final ErrorHandler errorHandler;

    @FXML
    private BookViewController bookViewController;

    @FXML
    private SearchBarController searchBarController;

    @FXML
    private MenuItem newBookButton;

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
    private void newBook() {
        try {
            createNewBookStage().showAndWait();
        } catch (IOException e) {
            errorHandler.handle(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBarController.setMediator(this);
    }

    @Override
    public void notify(ActionEvent event) {
        if (event.getSource() == searchBarController) {
            bookViewController.setSearchStrategy(searchBarController.getSearchStrategy());
        } else if (event.getSource() == newBookButton) {
            newBook();
        }
    }
}
