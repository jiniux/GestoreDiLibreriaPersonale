package it.jiniux.gdlp.presentation.javafx;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.core.application.DataAccessProvider;
import it.jiniux.gdlp.core.application.EventBus;
import it.jiniux.gdlp.infrastructure.json.JsonDataAccessProvider;
import it.jiniux.gdlp.presentation.javafx.errors.AlertErrorHandler;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.errors.LoggerErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import lombok.Getter;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ServiceLocator {
    private static ServiceLocator instance;

    @Getter
    private final AlertFactory alertFactory;

    @Getter
    private final FXMLFactory FXMLFactory;

    @Getter
    private final Localization localization;

    @Getter
    private final ErrorHandler errorHandler;

    @Getter
    private final BookService bookService;

    @Getter
    private final EventBus eventBus;

    @Getter
    private final DataAccessProvider dataAccessProvider;

    @Getter
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    private ErrorHandler configureErrorHandler() {
        LoggerErrorHandler loggerErrorHandler = new LoggerErrorHandler();
        loggerErrorHandler.setNext(new AlertErrorHandler(alertFactory));

        return loggerErrorHandler;
    }

    private ServiceLocator() {
        this.localization = new Localization();
        this.alertFactory = new AlertFactory(this.localization);
        this.FXMLFactory = new FXMLFactory(this.localization);
        this.errorHandler = configureErrorHandler();
        this.eventBus = new EventBus();
        this.dataAccessProvider = new JsonDataAccessProvider();
        this.bookService = new BookService(dataAccessProvider, eventBus);
    }

    public static synchronized ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }
}
