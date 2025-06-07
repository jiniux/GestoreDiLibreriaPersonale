package it.jiniux.gdlp.presentation.javafx;

import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.fxml.FXMLLoader;

import java.io.IOException;

public class FXMLFactory {
    private final Localization localization;

    public FXMLFactory(Localization localization) {
        this.localization = localization;
    }

    private FXMLLoader createFromFxml(String fxmlPath) throws IOException {
        return new FXMLLoader(getClass().getResource(fxmlPath), localization.getResourceBundle());
    }

    public FXMLLoader createEdition() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/addbook/edition/EditionComponent.fxml");
    }

    public FXMLLoader createDashboard() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/Dashboard.fxml");
    }

    public FXMLLoader createNewBookScene() throws IOException {
        return createFromFxml("/it/jiniux/gdlp/presentation/javafx/views/AddBook.fxml");
    }
}
