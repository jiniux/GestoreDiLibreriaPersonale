package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.presentation.javafx.FXMLFactory;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.CompositeValidable;
import it.jiniux.gdlp.presentation.javafx.common.Mediator;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition.EditionComponentController;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class BookEditionsController extends CompositeValidable implements Initializable, Mediator<Event> {
    private final FXMLFactory FXMLFactory;
    private final ErrorHandler errorHandler;

    @FXML private Button addEditionButton;

    @FXML private VBox additionalEditionsContainer;

    @FXML private EditionComponentController firstEditionController;
    
    private final List<EditionComponentController> editionControllers = new ArrayList<>();
    
    public BookEditionsController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.FXMLFactory = serviceLocator.getFXMLFactory();
        this.errorHandler = serviceLocator.getErrorHandler();
    }

    private EditionComponentController addEdition() {
        try {
            FXMLLoader loader = FXMLFactory.createEdition();
            Parent parent = loader.load();
            additionalEditionsContainer.getChildren().add(parent);

            EditionComponentController controller = loader.getController();
            controller.setEditionIndex(editionControllers.size());
            controller.setOnRemoveEdition(() -> {
                additionalEditionsContainer.getChildren().remove(parent);
                editionControllers.remove(controller);
                removeValidable(controller);
            });
            controller.showRemoveEditionButton();

            editionControllers.add(controller);
            addValidable(controller);

            return controller;
        } catch (IOException e) {
            errorHandler.handle(e);
        }

        return null;
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
    
    public void setEditions(List<BookDto.Edition> editions) {
        // Clear existing additional editions
        additionalEditionsContainer.getChildren().clear();
        editionControllers.clear();
        
        // Add the first edition controller back
        editionControllers.add(firstEditionController);
        
        // Set the first edition if available
        if (!editions.isEmpty()) {
            firstEditionController.setEdition(editions.get(0));
            
            // Add all additional editions
            for (int i = 1; i < editions.size(); i++) {
                EditionComponentController controller = addEdition();
                controller.setEdition(editions.get(i));
            }
        }
    }

    @Override
    public void notify(Event event) {
        if (event.getSource() == addEditionButton) {
            addEdition();
        }
    }
}
