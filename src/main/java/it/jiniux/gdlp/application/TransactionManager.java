package it.jiniux.gdlp.application;

import it.jiniux.gdlp.domain.exceptions.DomainException;

public interface TransactionManager {
    default void execute(Transaction transaction) throws DomainException {
        execute(transaction, false);
    }

    void execute(Transaction transaction, boolean readOnly) throws DomainException;

    default <T> T execute(TransactionWithResult<T> transaction) throws DomainException {
        return execute(transaction, false);
    }

    <T> T execute(TransactionWithResult<T> transaction, boolean readOnly) throws DomainException;
}
