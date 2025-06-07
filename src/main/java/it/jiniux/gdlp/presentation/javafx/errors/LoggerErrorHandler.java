package it.jiniux.gdlp.presentation.javafx.errors;

import java.util.logging.Logger;

public class LoggerErrorHandler extends BaseErrorHandler {
    private final Logger logger;

    public LoggerErrorHandler() {
        this.logger = Logger.getLogger(getClass().getName());
    }

    @Override
    public void handle(Throwable e) {
        if (e != null) {
            StringBuilder message = new StringBuilder("An error occurred: " + e.getMessage());
            for (StackTraceElement element : e.getStackTrace()) {
                message.append("\n\tat ").append(element);
            }
            logger.severe(message.toString());
        }

        super.handle(e);
    }
}
