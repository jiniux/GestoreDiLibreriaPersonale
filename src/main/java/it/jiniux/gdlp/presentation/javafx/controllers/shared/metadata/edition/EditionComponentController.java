package it.jiniux.gdlp.presentation.javafx.controllers.shared.metadata.edition;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.CompositeValidable;
import it.jiniux.gdlp.presentation.javafx.common.Validable;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TitledPane;
import lombok.Getter;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class EditionComponentController extends CompositeValidable implements Initializable {
    private final Localization localization;
    
    @FXML private TitledPane editionPane;
    @FXML private IsbnInputController isbnInputController;
    @FXML private PublisherInputController publisherInputController;
    @FXML private AdditionalAuthorsInputController additionalAuthorsInputController;
    @FXML private EditionDetailsInputController editionDetailsInputController;
    @FXML private FormatInputController formatInputController;
    @FXML private LanguageInputController languageInputController;
    @FXML private PublicationYearInputController publicationYearInputController;
    @FXML private CoverInputController coverInputController;
    
    @Getter
    private int editionIndex = 0;
    
    public EditionComponentController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }
    
    public void setEditionIndex(int index) {
        this.editionIndex = index;
        editionPane.setText(localization.get(LocalizationString.EDITION_TITLE_PREFIX) + " " + (index + 1));
    }

    // Methods to get data from each component
    public String getIsbn() {
        return isbnInputController.getIsbn();
    }
    
    public String getPublisher() {
        return publisherInputController.getPublisher();
    }
    
    public List<String> getAdditionalAuthors() {
        return additionalAuthorsInputController.getAuthors();
    }
    
    public int getEditionNumber() {
        return editionDetailsInputController.getEditionNumber();
    }
    
    public String getEditionTitle() {
        return editionDetailsInputController.getEditionTitle();
    }
    
    public String getFormat() {
        return formatInputController.getFormat();
    }
    
    public String getLanguage() {
        return languageInputController.getLanguage();
    }
    
    public Integer getPublicationYear() {
        return publicationYearInputController.getPublicationYear();
    }
    
    public String getCoverPath() {
        return coverInputController.getCoverPath();
    }

    public BookDto.Edition getEdition() {
        BookDto.Edition edition = new BookDto.Edition();
        edition.setEditionTitle(getEditionTitle());
        edition.setEditionNumber(getEditionNumber());
        edition.setIsbn(getIsbn());
        edition.setPublisherName(getPublisher());
        edition.setAdditionalAuthors(getAdditionalAuthors().stream().toList());
        edition.setFormat(getFormat());
        edition.setLanguage(getLanguage());
        edition.setPublicationYear(getPublicationYear());

        return edition;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setEditionIndex(0);
        addValidable(isbnInputController);
        addValidable(publisherInputController);
        addValidable(additionalAuthorsInputController);
        addValidable(editionDetailsInputController);
        addValidable(formatInputController);
        addValidable(languageInputController);
        addValidable(publicationYearInputController);
    }
}
