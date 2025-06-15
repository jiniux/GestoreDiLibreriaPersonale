package it.jiniux.gdlp.presentation.javafx;

import it.jiniux.gdlp.core.application.BookService;
import it.jiniux.gdlp.core.application.DataAccessProvider;
import it.jiniux.gdlp.core.application.EventBus;
import it.jiniux.gdlp.infrastructure.json.JsonDataAccessProvider;
import it.jiniux.gdlp.presentation.javafx.common.DomainErrorAlertFactory;
import it.jiniux.gdlp.presentation.javafx.errors.DomainErrorHandler;
import it.jiniux.gdlp.presentation.javafx.errors.GenericErrorHandler;
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

    private BookService bookService;

    @Getter
    private final EventBus eventBus;

    private DataAccessProvider dataAccessProvider;

    @Getter
    private final ExecutorService executorService = Executors.newCachedThreadPool();

    @Getter
    private final DomainErrorAlertFactory domainErrorAlertFactory;

    private ErrorHandler configureErrorHandler() {
        LoggerErrorHandler loggerErrorHandler = new LoggerErrorHandler();
        GenericErrorHandler genericErrorHandler = new GenericErrorHandler(alertFactory);
        DomainErrorHandler domainErrorHandler = new DomainErrorHandler(domainErrorAlertFactory);

        loggerErrorHandler.setNext(domainErrorHandler);
        domainErrorHandler.setNext(genericErrorHandler);

        return loggerErrorHandler;
    }

    public BookService getBookService() {
        if (bookService == null) {
            throw new IllegalStateException("DataAccessProvider is not set. " +
                    "Call setDataAccessProvider() before using BookService.");
        }

        return bookService;
    }

    public synchronized void setJsonDataAccessProvider(JsonDataAccessProvider dataAccessProvider) {
        if (this.dataAccessProvider != null) {
            throw new IllegalStateException("DataAccessProvider is already set. ");
        }

        this.dataAccessProvider = dataAccessProvider;
        this.bookService = new BookService(dataAccessProvider, eventBus);
    }

    public synchronized DataAccessProvider getDataAccessProvider() {
        if (dataAccessProvider == null) {
            throw new IllegalStateException("DataAccessProvider is yet to be set. " +
                    "Call setDataAccessProvider() before using it.");
        }

        return dataAccessProvider;
    }


    private ServiceLocator() {
        this.localization = new Localization();
        this.alertFactory = new AlertFactory(this.localization);
        this.domainErrorAlertFactory = new DomainErrorAlertFactory(alertFactory);
        this.FXMLFactory = new FXMLFactory(this.localization);
        this.errorHandler = configureErrorHandler();
        this.eventBus = new EventBus();
    }

    public static synchronized ServiceLocator getInstance() {
        if (instance == null) {
            instance = new ServiceLocator();
        }
        return instance;
    }
}
