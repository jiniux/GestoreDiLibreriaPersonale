package it.jiniux.gdlp.presentation.javafx.errors;

public abstract class BaseErrorHandler implements ErrorHandler {
    private ErrorHandler next;

    public ErrorHandler getNext() {
        return next;
    }

    @Override
    public void handle(Throwable e) {
        ErrorHandler nextHandler = getNext();

        if (nextHandler != null) {
            nextHandler.handle(e);
        }
    }

    public void setNext(ErrorHandler next) {
        this.next = next;
    }
}
