package it.jiniux.gdlp.presentation.javafx.errors;

import it.jiniux.gdlp.core.domain.exceptions.DomainException;
import it.jiniux.gdlp.presentation.javafx.common.DomainErrorAlertFactory;
import it.jiniux.gdlp.utility.ExceptionChainVisitor;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DomainErrorHandler extends BaseErrorHandler {
    private final DomainErrorAlertFactory domainErrorAlertFactory;

    @Override
    public void handle(Throwable e) {
        DomainException domainException = ExceptionChainVisitor.findExceptionInChain(e, DomainException.class);

        if (domainException != null) {
            domainErrorAlertFactory.createAlertForException(domainException).showAndWait();
        } else {
            super.handle(e);
        }
    }
}
