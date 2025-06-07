package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.presentation.javafx.FXMLFactory;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.CompositeValidable;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition.EditionComponentController;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import lombok.Getter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookEditionsController extends CompositeValidable implements Initializable {
    private final FXMLFactory FXMLFactory;
    private final ErrorHandler errorHandler;

    @FXML private VBox additionalEditionsContainer;

    @FXML private EditionComponentController firstEditionController;
    
    private final List<EditionComponentController> editionControllers = new ArrayList<>();
    
    public BookEditionsController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.FXMLFactory = serviceLocator.getFXMLFactory();
        this.errorHandler = serviceLocator.getErrorHandler();
    }
    
    @FXML
    public void handleAddEdition(ActionEvent event) {
        try {
            FXMLLoader loader = FXMLFactory.createEdition();
            additionalEditionsContainer.getChildren().add(loader.load());

            EditionComponentController controller = loader.getController();
            controller.setEditionIndex(editionControllers.size());

            editionControllers.add(controller);
        } catch (IOException e) {
            errorHandler.handle(e);
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        editionControllers.add(firstEditionController);
        addValidable(firstEditionController);
    }

    public List<BookDto.Edition> getEditions() {
        List<BookDto.Edition> editions = new ArrayList<>();

        for (EditionComponentController controller : editionControllers) {
            if (controller.isValid()) {
                editions.add(controller.getEdition());
            } else {
                throw new IllegalStateException("One or more editions are invalid.");
            }
        }

        return editions;
    }
}
