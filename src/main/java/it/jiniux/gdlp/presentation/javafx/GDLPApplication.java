package it.jiniux.gdlp.presentation.javafx;

import it.jiniux.gdlp.core.application.DataAccessProvider;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class GDLPApplication extends Application {
    private final FXMLFactory FXMLFactory;
    private final Localization localization;
    private final ErrorHandler errorHandler;
    private final DataAccessProvider dataAccessProvider;

    public GDLPApplication() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        this.FXMLFactory = serviceLocator.getFXMLFactory();
        this.localization = serviceLocator.getLocalization();
        this.errorHandler = serviceLocator.getErrorHandler();
        this.dataAccessProvider = serviceLocator.getDataAccessProvider();
    }

    @Override
    public void stop() throws Exception {
        this.dataAccessProvider.gracefullyClose();
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            Scene dashboardScene = new Scene(FXMLFactory.createDashboard().load());

            primaryStage.setScene(dashboardScene);
            primaryStage.setTitle(localization.get(LocalizationString.TITLE));
            primaryStage.show();
        } catch (IOException e) {
            errorHandler.handle(e);
        }
    }
}
