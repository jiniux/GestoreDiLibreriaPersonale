package it.jiniux.gdlp.presentation.javafx.controllers;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.core.application.Event;
import it.jiniux.gdlp.core.application.EventBus;
import it.jiniux.gdlp.core.application.Page;
import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.core.application.dtos.ReadingStatusDto;
import it.jiniux.gdlp.core.domain.exceptions.DomainException;
import it.jiniux.gdlp.presentation.javafx.FXMLFactory;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import it.jiniux.gdlp.utility.observer.Observer;
import javafx.application.Platform;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.ComboBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.layout.BorderPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.stream.IntStream;

public class BookViewController implements Initializable, Observer<Event> {
    private final BookService bookService;
    private final EventBus eventBus;
    private final ExecutorService executorService;
    private final ErrorHandler errorHandler;
    private final FXMLFactory fxmlFactory;
    private final Localization localization;

    @FXML public TableView<BookDto> tableView;
    @FXML public TableColumn<BookDto, String> titleColumn;
    @FXML public TableColumn<BookDto, String> authorColumn;
    @FXML public TableColumn<BookDto, String> ratingColumn;
    @FXML public TableColumn<BookDto, String> statusColumn;
    @FXML public Label loadingLabel;
    @FXML public BorderPane root;
    @FXML public Button prevPageButton;
    @FXML public Button nextPageButton;
    @FXML public Label pageInfoLabel;

    private int currentPage = 0;
    private int totalPages = 1;
    private static final int LIMIT = 10;
    private CompletableFuture<Void> bookFuture;

    public BookViewController() {
        ServiceLocator locator = ServiceLocator.getInstance();
        this.eventBus = locator.getEventBus();
        this.executorService = locator.getExecutorService();
        this.bookService = locator.getBookService();
        this.errorHandler = locator.getErrorHandler();
        this.fxmlFactory = locator.getFXMLFactory();
        this.localization = locator.getLocalization();
    }

    private String mapReadingStatus(ReadingStatusDto readingStatusDto) {
        return switch (readingStatusDto) {
            case READ -> localization.get(LocalizationString.STATUS_READ);
            case READING -> localization.get(LocalizationString.STATUS_READING);
            case TO_READ -> localization.get(LocalizationString.STATUS_TO_READ);
            case ABANDONED -> localization.get(LocalizationString.STATUS_ABANDONED);
        };
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        eventBus.attach(this);

        root.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene == null) onRemoved();
        });

        setupColumns();
        setupRowDoubleClickHandler();
        updatePageControls();
        reloadBooks();
    }

    private void setupRowDoubleClickHandler() {
        tableView.setRowFactory(tv -> {
            TableRow<BookDto> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    BookDto book = row.getItem();
                    handleEditBook(book);
                }
            });
            return row;
        });
    }

    private void handleEditBook(BookDto book) {
        try {
            FXMLLoader loader = fxmlFactory.createEditBook();
            Scene scene = new Scene(loader.load());

            EditBookController controller = loader.getController();
            controller.setBook(book);

            Stage stage = new Stage();
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setScene(scene);
            stage.setTitle(localization.get(LocalizationString.EDIT_BOOK_WINDOW_TITLE));
            stage.showAndWait();

            reloadBooks();
        } catch (IOException e) {
            errorHandler.handle(e);
        }
    }

    private void setupColumns() {
        tableView.setEditable(true);

        setupTitleColumn();
        setupAuthorColumn();
        setupRatingColumn();
        setupStatusColumn();
    }

    private void setupTitleColumn() {
        titleColumn.setEditable(false);
        titleColumn.setCellValueFactory(new PropertyValueFactory<>("title"));
        titleColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private void setupAuthorColumn() {
        authorColumn.setEditable(false);
        authorColumn.setCellValueFactory(cd -> new SimpleStringProperty(String.join(", ", cd.getValue().getAuthors())));
        authorColumn.setCellFactory(TextFieldTableCell.forTableColumn());
    }

    private String mapRatingStars(Integer rating) {
        if (rating == null || rating < 1 || rating > 5) {
            return "☆☆☆☆☆"; // Default to no stars
        }
        return "★".repeat(rating) + "☆".repeat(5 - rating);
    }

    private void editBook(BookDto bookDto) {
        showLoading();

        bookFuture = CompletableFuture.runAsync(() -> {
                    try {
                        bookService.editBook(bookDto);
                    } catch (DomainException e) {
                        throw new RuntimeException(e);
                    }
                }, executorService)
                .exceptionally(e -> {
                    errorHandler.handle(e);
                    return null;
                });
    }

    private void setupRatingColumn() {
        ratingColumn.setCellValueFactory(cd -> new SimpleObjectProperty<>(mapRatingStars(cd.getValue().getRating())));
        ratingColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(
                IntStream.range(1, 6).mapToObj(i -> "★".repeat(i) + "☆".repeat(5 - i)).toList())));
        ratingColumn.setOnEditCommit(event -> {
            BookDto bookDto = event.getRowValue();
            String newRating = event.getNewValue();

            long rating = newRating.chars().filter(ch -> ch == '★').count();
            if (rating < 1 || rating > 5) {
                errorHandler.handle(new IllegalArgumentException("Invalid rating: " + newRating));
                return;
            }

            bookDto.setRating((int)rating);
            editBook(bookDto);
        });
    }

    private void setupStatusColumn() {
        statusColumn.setEditable(true);
        statusColumn.setCellValueFactory(cd -> new SimpleObjectProperty<>(mapReadingStatus(cd.getValue().getReadingStatus())));
        statusColumn.setCellFactory(ComboBoxTableCell.forTableColumn(FXCollections.observableArrayList(Arrays.stream(ReadingStatusDto.values()).map(this::mapReadingStatus).toList())));
        statusColumn.setOnEditCommit(event -> {
            BookDto bookDto = event.getRowValue();
            String newStatus = event.getNewValue();

            ReadingStatusDto readingStatusDto = Arrays.stream(ReadingStatusDto.values())
                    .filter(status -> mapReadingStatus(status).equals(newStatus))
                    .findFirst()
                    .orElseThrow(() -> new IllegalArgumentException("Invalid reading status: " + newStatus));

            bookDto.setReadingStatus(readingStatusDto);

            editBook(bookDto);
        });
    }

    private void showLoading() {
        tableView.setDisable(true);
        tableView.setEffect(new GaussianBlur(10));
        loadingLabel.setVisible(true);
        loadingLabel.setManaged(true);
        prevPageButton.setDisable(true);
        nextPageButton.setDisable(true);
    }

    private void hideLoading() {
        tableView.setDisable(false);
        tableView.setEffect(null);
        loadingLabel.setVisible(false);
        loadingLabel.setManaged(false);
        updatePageControls();
    }

    public void reloadBooks() {
        showLoading();
        if (bookFuture != null) bookFuture.cancel(true);

        bookFuture = CompletableFuture.supplyAsync(() -> bookService.findBooks(currentPage, LIMIT), executorService)
                .thenAccept(books -> Platform.runLater(() -> showBooks(books)))
                .exceptionally(e -> {
                    errorHandler.handle(e);
                    return null;
                });
    }

    private void showBooks(Page<BookDto> books) {
        hideLoading();
        tableView.setItems(FXCollections.observableArrayList(books.getElements()));
        totalPages = books.getTotalPages();
        updatePageControls();
    }

    private void updatePageControls() {
        pageInfoLabel.setText((currentPage + 1) + "/" + totalPages);
        
        prevPageButton.setDisable(currentPage <= 0);
        nextPageButton.setDisable(currentPage >= totalPages - 1);
    }

    @FXML
    public void handlePreviousPage() {
        if (currentPage > 0) {
            currentPage--;
            reloadBooks();
        }
    }

    @FXML
    public void handleNextPage() {
        if (currentPage < totalPages - 1) {
            currentPage++;
            reloadBooks();
        }
    }

    public void setPage(int page) {
        this.currentPage = page;
        updatePageControls();
    }

    public void onRemoved() {
        eventBus.detach(this);
    }

    @Override
    public void update(Event event) {
        if (event instanceof Event.BookUpdated) {
            reloadBooks();
        } else if (event instanceof Event.BookCreated || event instanceof Event.BookDeleted) {
            setPage(0);
            reloadBooks();
        }
    }
}
