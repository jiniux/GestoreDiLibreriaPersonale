package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.CompositeValidable;
import it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.bookinfo.*;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

public class BookInfoController extends CompositeValidable implements Initializable {
    private final Localization localization;

    @FXML
    private TitleInputController titleComponentController;

    @FXML
    private AuthorsInputController authorsComponentController;

    @FXML
    private GenresInputController genresComponentController;
    
    @FXML
    private DescriptionInputController descriptionComponentController;
    
    @FXML
    private RatingInputController ratingComponentController;
    
    @FXML
    private ReadingStatusInputController readingStatusComponentController;

    public BookInfoController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        addValidable(titleComponentController);
        addValidable(authorsComponentController);
        addValidable(genresComponentController);
        addValidable(descriptionComponentController);
        addValidable(ratingComponentController);
        addValidable(readingStatusComponentController);
    }
    
    public String getTitle() {
        return titleComponentController.getTitle();
    }

    public List<String> getAuthors() {
        return authorsComponentController.getAuthors();
    }

    public List<String> getGenres() {
        return genresComponentController.getGenres();
    }

    public String getDescription() {
        return descriptionComponentController.getDescription();
    }

    public Integer getRating() {
        Optional<Integer> rating = ratingComponentController.getRating();
        return rating.orElse(null);
    }

    public String getReadingStatus() {
        Optional<String> readingStatus = readingStatusComponentController.getReadingStatus();
        return readingStatus.orElse(null);
    }
}
