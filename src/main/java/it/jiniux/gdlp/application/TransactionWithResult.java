package it.jiniux.gdlp.application;

import it.jiniux.gdlp.domain.exceptions.DomainException;

public interface TransactionWithResult<T> {
    T execute() throws DomainException;
}
