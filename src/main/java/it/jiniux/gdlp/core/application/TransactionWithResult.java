package it.jiniux.gdlp.core.application;

import it.jiniux.gdlp.core.domain.exceptions.DomainException;

public interface TransactionWithResult<T> {
    T execute() throws DomainException;
}
