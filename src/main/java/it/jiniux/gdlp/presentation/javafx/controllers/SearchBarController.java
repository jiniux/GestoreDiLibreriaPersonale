package it.jiniux.gdlp.presentation.javafx.controllers;

import it.jiniux.gdlp.core.application.dtos.BookFilterDto;
import it.jiniux.gdlp.presentation.javafx.FXMLFactory;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.Mediator;
import it.jiniux.gdlp.presentation.javafx.controllers.search.SearchCreateCompositeController;
import it.jiniux.gdlp.presentation.javafx.controllers.search.SearchStrategy;
import it.jiniux.gdlp.presentation.javafx.controllers.search.strategies.FilterSearchStrategy;
import it.jiniux.gdlp.presentation.javafx.controllers.search.strategies.NaiveSearchStrategy;
import it.jiniux.gdlp.presentation.javafx.controllers.search.strategies.QuerySearchStrategy;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.Setter;

import java.io.IOException;

public class SearchBarController implements Mediator<Event> {
    private final Localization localization;
    private final FXMLFactory fxmlFactory;
    private final ErrorHandler errorHandler;

    public SearchBarController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
        this.fxmlFactory = serviceLocator.getFXMLFactory();
        this.errorHandler = serviceLocator.getErrorHandler();
    }

    @Setter
    private Mediator<ActionEvent> mediator;

    @FXML
    private TextField searchTextField;

    @FXML
    private Button searchButton;

    @FXML
    private Button filterButton;

    @FXML
    private Button clearButton;

    @FXML
    private void initialize() {
        updateButtons();
    }
    
    private void updateButtons() {
        searchTextField.setDisable(searchStrategy instanceof QuerySearchStrategy);
        searchButton.setDisable(searchStrategy instanceof QuerySearchStrategy);
    }

    @Getter
    private SearchStrategy searchStrategy = NaiveSearchStrategy.getInstance();

    @FXML
    public void search() {
        updateQuery(searchTextField.getText());
    }

    public void updateQuery(String query) {
        searchStrategy = new QuerySearchStrategy(query);
        mediator.notify(new ActionEvent(this, null));
        updateButtons();
    }

    @Override
    public void notify(Event event) {
        if (event.getSource() == searchButton) {
            search();
        } else if (event.getSource() == filterButton) {
            filter();
        } else if (event.getSource() == clearButton) {
            clear();
        }
    }

    @FXML
    public void filter() {
        BookFilterDto filter = null;

        if (searchStrategy instanceof FilterSearchStrategy filterSearchStrategy) {
            filter = filterSearchStrategy.getFilter();
        }

        showSearchCreateCompositeDialog(filter);
    }

    @FXML
    public void clear() {
        searchStrategy = NaiveSearchStrategy.getInstance();
        mediator.notify(new ActionEvent(this, null));
        updateButtons();
    }

    private void updateFilter(BookFilterDto filter) {
        searchStrategy = new FilterSearchStrategy(filter);
        mediator.notify(new ActionEvent(this, null));
        updateButtons();
    }

    public void showSearchCreateCompositeDialog(BookFilterDto oldFilter) {
        try {
            FXMLLoader loader = fxmlFactory.createSearchCreateComposite();
            loader.setResources(localization.getResourceBundle());
            Parent root = loader.load();

            SearchCreateCompositeController controller = loader.getController();

            Stage stage = new Stage();
            stage.setTitle(localization.get(LocalizationString.SEARCH_CREATE_COMPOSITE_WINDOW_TITLE));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMinWidth(500);
            stage.setMinHeight(400);

            if (oldFilter != null) {
                controller.setFilter(oldFilter);
            }

            stage.showAndWait();

            BookFilterDto filter = controller.getResultNode().map(n -> {
                BookFilterDto bookFilterDto = new BookFilterDto();
                bookFilterDto.setRoot(n);
                return bookFilterDto;
            }).orElse(null);

            if (filter != null && !filter.getRoot().getChildren().isEmpty()) {
                updateFilter(filter);
            }
        } catch (IOException e) {
            errorHandler.handle(e);
        }
    }
}
