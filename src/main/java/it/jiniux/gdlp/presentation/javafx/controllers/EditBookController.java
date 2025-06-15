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
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import it.jiniux.gdlp.utility.ExceptionChainVisitor;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.Collectors;

public class EditBookController extends CompositeValidable implements Initializable, Mediator<ActionEvent> {
    private final BookService bookService;
    private final AlertFactory alertFactory;
    private final ExecutorService executor;
    private final Localization localization;
    private final ErrorHandler errorHandler;

    private BookDto bookToEdit;

    public EditBookController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.alertFactory = serviceLocator.getAlertFactory();
        this.bookService = serviceLocator.getBookService();
        this.executor = serviceLocator.getExecutorService();
        this.localization = serviceLocator.getLocalization();
        this.errorHandler = serviceLocator.getErrorHandler();
    }

    @FXML private Button saveButton;
    @FXML private Button cancelButton;
    @FXML private Button removeButton;

    @FXML private TabPane tabPane;
    @FXML private Label savingLabel;

    @FXML private BookInfoController bookInfoFormController;
    @FXML private BookEditionsController bookEditionsFormController;

    public void setBook(BookDto book) {
        this.bookToEdit = book;
        populateFormWithBookData();
    }

    private void populateFormWithBookData() {
        bookInfoFormController.setTitle(bookToEdit.getTitle());
        bookInfoFormController.setDescription(bookToEdit.getDescription());
        bookInfoFormController.setAuthors(bookToEdit.getAuthors());
        bookInfoFormController.setGenres(bookToEdit.getGenres().stream()
                .map(GenreDto::getName)
                .collect(Collectors.toList()));
        bookInfoFormController.setRating(bookToEdit.getRating());
        bookInfoFormController.setReadingStatus(bookToEdit.getReadingStatus());
        bookEditionsFormController.setEditions(bookToEdit.getEditions());
    }

    public BookDto createUpdatedBook() {
        BookDto updatedBook = new BookDto();
        updatedBook.setId(bookToEdit.getId()); // Keep the original ID
        updatedBook.setTitle(bookInfoFormController.getTitle());
        updatedBook.setDescription(bookInfoFormController.getDescription());
        updatedBook.setEditions(bookEditionsFormController.getEditions());
        updatedBook.setGenres(bookInfoFormController.getGenres().stream().map(GenreDto::new).toList());
        updatedBook.setAuthors(bookInfoFormController.getAuthors());
        updatedBook.setRating(bookInfoFormController.getRating());
        updatedBook.setReadingStatus(bookInfoFormController.getReadingStatus());
        return updatedBook;
    }

    public void handleSaveSuccess() {
        alertFactory.createAlert(AlertVariant.BOOK_EDITED_SUCCESSFULLY, bookInfoFormController.getTitle()).showAndWait();
        savingLabel.setVisible(false);
        closeWindow();
    }

    public void handleSaveError(Throwable exception) {
        errorHandler.handle(exception);
        savingLabel.setVisible(false);
        enableControls();
    }
    
    public void handleRemoveSuccess() {
        alertFactory.createAlert(AlertVariant.BOOK_REMOVED_SUCCESSFULLY, bookToEdit.getTitle()).showAndWait();
        savingLabel.setVisible(false);
        closeWindow();
    }

    public void handleRemoveError(Throwable exception) {
        errorHandler.handle(exception);
        savingLabel.setVisible(false);
        enableControls();
    }

    public void enableControls() {
        saveButton.setDisable(false);
        cancelButton.setDisable(false);
        removeButton.setDisable(false);
        tabPane.setDisable(false);
    }

    public void disableControls() {
        saveButton.setDisable(true);
        cancelButton.setDisable(true);
        removeButton.setDisable(true);
        tabPane.setDisable(true);
    }

    @FXML
    private void saveBook() {
        validate();

        if (!isValid()) {
            this.alertFactory.createAlert(AlertVariant.INVALID_EDIT_BOOK_FORM).showAndWait();
            return;
        }

        disableControls();
        savingLabel.setVisible(true);

        BookDto updatedBook = createUpdatedBook();

        CompletableFuture.runAsync(() -> {
            try {
                bookService.editBook(updatedBook);
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
    private void removeBook() {
        ButtonType pressedButton = alertFactory.createAlert(
            AlertVariant.CONFIRM_REMOVE_BOOK, bookToEdit.getTitle()
        ).showAndWait().orElse(ButtonType.NO);

        if (pressedButton == ButtonType.NO || pressedButton == ButtonType.CANCEL) {
            return;
        }

        disableControls();
        savingLabel.setVisible(true);
        savingLabel.setText(localization.get(LocalizationString.REMOVING_BOOK));

        CompletableFuture.runAsync(() -> {
            try {
                bookService.deleteBook(bookToEdit.getId());
            } catch (DomainException e) {
                throw new RuntimeException(e);
            }
        }, executor).thenAccept(v -> {
            Platform.runLater(this::handleRemoveSuccess);
        }).exceptionally(e -> {
            Platform.runLater(() -> handleRemoveError(e));
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
    public void notify(ActionEvent event) {
        if (event.getSource() == removeButton) {
            removeBook();
        } else if (event.getSource() == saveButton) {
            saveBook();
        } else if (event.getSource() == cancelButton) {
            cancel();
        }
    }
}
