package it.jiniux.gdlp.presentation.javafx.controllers;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.GenreDto;
import it.jiniux.gdlp.core.domain.exceptions.DomainException;
import it.jiniux.gdlp.presentation.javafx.AlertFactory;
import it.jiniux.gdlp.presentation.javafx.AlertVariant;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.CompositeValidable;
import it.jiniux.gdlp.presentation.javafx.common.DomainErrorAlertFactory;
import it.jiniux.gdlp.presentation.javafx.common.Mediator;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.BookEditionsController;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.BookInfoController;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.utility.ExceptionChainVisitor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

public class AddBookController extends CompositeValidable implements  Initializable, Mediator<Event> {
    private final BookService bookService;
    private final AlertFactory alertFactory;
    private final ExecutorService executor;
    private final ErrorHandler errorHandler;

    public AddBookController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        this.alertFactory = serviceLocator.getAlertFactory();
        this.bookService = serviceLocator.getBookService();
        this.executor = serviceLocator.getExecutorService();
        this.errorHandler = serviceLocator.getErrorHandler();
    }

    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    @FXML private TabPane tabPane;
    @FXML private Label savingLabel;

    @FXML private BookInfoController bookInfoFormController;
    @FXML private BookEditionsController bookEditionsFormController;

    private final Localization localization = new Localization();

    public BookDto createBook() {
        BookDto bookDto = new BookDto();
        bookDto.setTitle(bookInfoFormController.getTitle());
        bookDto.setDescription(bookInfoFormController.getDescription());
        bookDto.setEditions(bookEditionsFormController.getEditions());
        bookDto.setGenres(bookInfoFormController.getGenres().stream().map(GenreDto::new).toList());
        bookDto.setAuthors(bookInfoFormController.getAuthors());
        bookDto.setEditions(bookEditionsFormController.getEditions());
        return bookDto;
    }

    public void handleSaveSuccess() {
        alertFactory.createAlert(AlertVariant.BOOK_ADDED_SUCCESSFULLY, bookInfoFormController.getTitle()).showAndWait();
        savingLabel.setVisible(false);
        closeWindow();
    }

    public void handleSaveError(Throwable exception) {
        errorHandler.handle(exception);
        savingLabel.setVisible(false);
        enableControls();
    }

    public void enableControls() {
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        tabPane.setDisable(false);
    }

    public void disableControls() {
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        tabPane.setDisable(true);
    }

    @FXML
    private void save() {
        validate();

        if (!isValid()) {
            this.alertFactory.createAlert(AlertVariant.INVALID_ADD_BOOK_FORM).showAndWait();
            return;
        }

        disableControls();
        savingLabel.setVisible(true);

        BookDto bookDto = createBook();

        CompletableFuture.runAsync(() -> {
            try {
                bookService.createBook(bookDto);
            } catch (DomainException e) {
                throw new RuntimeException(e);
            }
        }, executor).thenAccept(v -> {
            Platform.runLater(this::handleSaveSuccess);
        }).exceptionally(e -> {
            Platform.runLater(() -> handleSaveError(e));
            return null;
        });
    }

    @FXML
    private void cancel() {
        closeWindow();
    }

    private void closeWindow() {
        Stage stage = (Stage) cancelButton.getScene().getWindow();
        stage.close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addValidable(bookInfoFormController);
        addValidable(bookEditionsFormController);
    }

    @Override
    public void notify(Event event) {
        if (event.getSource() == saveButton) {
            save();
        } else if (event.getSource() == cancelButton) {
            cancel();
        }
    }
}
