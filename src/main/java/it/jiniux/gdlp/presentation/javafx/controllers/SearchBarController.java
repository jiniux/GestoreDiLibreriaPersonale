package it.jiniux.gdlp.presentation.javafx.controllers;

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
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;

public class SearchBarController {
    private final Localization localization;
    private final FXMLFactory fxmlFactory;
    private final ErrorHandler errorHandler;

    public SearchBarController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
        this.fxmlFactory = serviceLocator.getFXMLFactory();
        this.errorHandler = serviceLocator.getErrorHandler();
    }

    private BookFilterDto filter;

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
        boolean filterActive = (filter != null);
        searchTextField.setDisable(filterActive);
        searchButton.setDisable(filterActive);
    }

    @FXML
    public void onSearchAction(ActionEvent event) {
        BookFilterDto filter = new BookFilterDto();
        filter.addCriterion(BookFilterDto.Field.TITLE, BookFilterDto.FilterOperator.CONTAINS, searchTextField.getText());
        filter.addCriterion(BookFilterDto.Field.ANY_AUTHOR_NAME, BookFilterDto.FilterOperator.CONTAINS, searchTextField.getText());
        filter.addCriterion(BookFilterDto.Field.ANY_ISBN, BookFilterDto.FilterOperator.CONTAINS, searchTextField.getText());
        filter.addCriterion(BookFilterDto.Field.ANY_EDITION_TITLE, BookFilterDto.FilterOperator.CONTAINS, searchTextField.getText());
        filter.setOperator(BookFilterDto.LogicalOperator.OR);
        updateFilter(filter);
    }

    public interface FilterUpdateAction {
        void execute(BookFilterDto filter);
    }

    private FilterUpdateAction filterUpdateAction;

    public void setOnFilterUpdate(FilterUpdateAction filterUpdateAction) {
        this.filterUpdateAction = filterUpdateAction;
    }

    @FXML
    public void onFilterAction(ActionEvent event) {
        showSearchCreateCompositeDialog();
    }

    @FXML
    public void onClearFilterAction(ActionEvent event) {
        updateFilter(null);
    }

    private void updateFilter(BookFilterDto filter) {
        this.filter = filter;
        if (filterUpdateAction != null) {
            filterUpdateAction.execute(filter);
        }
        updateButtons();
    }

    public void showSearchCreateCompositeDialog() {
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
