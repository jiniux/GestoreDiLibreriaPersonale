package it.jiniux.gdlp.presentation.javafx.controllers;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.BookFilterDto;
import it.jiniux.gdlp.presentation.javafx.FXMLFactory;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.controllers.search.SearchCreateCompositeController;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;

public class DashboardController implements Initializable {
    private final FXMLFactory FXMLFactory;
    private final Localization localization;
    private final ErrorHandler errorHandler;

    @FXML
    private BookViewController bookViewController;

    @FXML
    private SearchBarController searchBarController;

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

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        searchBarController.setOnFilterUpdate(filter -> {
            bookViewController.setFilter(filter);
        });
    }
}
