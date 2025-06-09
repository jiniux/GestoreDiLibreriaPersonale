package it.jiniux.gdlp.presentation.javafx;

import it.jiniux.gdlp.core.application.dtos.BookDto;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;

import java.io.IOException;

public class FXMLFactory {
    private final Localization localization;

    public FXMLFactory(Localization localization) {
        this.localization = localization;
    }

    private <T> FXMLLoader getLoader(String fxmlFile) {
        FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlFile));
        loader.setResources(localization.getResourceBundle());
        return loader;
    }

    private FXMLLoader createFromFxml(String fxmlPath) throws IOException {
        return new FXMLLoader(getClass().getResource(fxmlPath), localization.getResourceBundle());
    }

    public FXMLLoader createEditBook() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/EditBook.fxml");
    }

    public FXMLLoader createEdition() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/metadata/edition/EditionComponent.fxml");
    }

    public FXMLLoader createDashboard() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/Dashboard.fxml");
    }

    public FXMLLoader createNewBookScene() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/AddBook.fxml");
    }

    public FXMLLoader createSearchCreateLeaf() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/search/SearchCreateLeaf.fxml");
    }

    public FXMLLoader createSearchCreateComposite() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/search/SearchCreateComposite.fxml");
    }
}
