package it.jiniux.gdlp.infrastructure.json;

import it.jiniux.gdlp.core.application.Transaction;
import it.jiniux.gdlp.core.application.TransactionManager;
import it.jiniux.gdlp.core.application.TransactionWithResult;
import it.jiniux.gdlp.core.domain.exceptions.DomainException;
import it.jiniux.gdlp.infrastructure.inmemory.InMemoryTransactionManager;
import lombok.Getter;

public class JsonTransactionManager implements TransactionManager {
    @Getter
    private final InMemoryTransactionManager inMemoryTransactionManager;

    public JsonTransactionManager(InMemoryTransactionManager inMemoryTransactionManager) {
        this.inMemoryTransactionManager = inMemoryTransactionManager;
    }

    @Override
    public void execute(Transaction transaction, boolean readOnly) throws DomainException {
        inMemoryTransactionManager.execute(transaction, readOnly);
    }

    @Override
    public <T> T execute(TransactionWithResult<T> transaction, boolean readOnly) throws DomainException {
        return inMemoryTransactionManager.execute(transaction, readOnly);
    }
}
