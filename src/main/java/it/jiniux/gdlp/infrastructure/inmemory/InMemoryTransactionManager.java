package it.jiniux.gdlp.infrastructure.inmemory;

import it.jiniux.gdlp.application.Transaction;
import it.jiniux.gdlp.application.TransactionManager;
import it.jiniux.gdlp.application.TransactionWithResult;
import it.jiniux.gdlp.domain.exceptions.DomainException;

import java.util.Optional;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class InMemoryTransactionManager implements TransactionManager {
    ThreadLocal<Optional<InMemoryTransactionState>> transactionState = ThreadLocal.withInitial(Optional::empty);

    private final ReadWriteLock rwLock = new ReentrantReadWriteLock();

    public Optional<InMemoryTransactionState> getCurrentTransactionState() {
        return transactionState.get();
    }

    public void acquireLock(boolean readOnly) {
        if (readOnly) {
            rwLock.readLock().lock();
        } else {
            rwLock.writeLock().lock();
        }
    }

    public void releaseLock(boolean readOnly) {
        if (readOnly) {
            rwLock.readLock().unlock();
        } else {
            rwLock.writeLock().unlock();
        }
    }

    public InMemoryTransactionState instantiateTransactionState(boolean readOnly) {
        InMemoryTransactionState state = getCurrentTransactionState()
                .orElseGet(() -> {
                    InMemoryTransactionState newState = new InMemoryTransactionState(readOnly);
                    transactionState.set(Optional.of(newState));

                    return newState;
                });

        transactionState.set(Optional.of(state));
        return state;
    }

    @Override
    public void execute(Transaction transaction, boolean readOnly) throws DomainException {
        acquireLock(readOnly);
        InMemoryTransactionState state = instantiateTransactionState(readOnly);

        try {
            transaction.execute();
            transactionState.set(Optional.empty());
        } catch (Exception e) {
            state.rollback();
            transactionState.set(Optional.empty());
            throw e;
        } finally {
            releaseLock(readOnly);
        }
    }

    @Override
    public <T> T execute(TransactionWithResult<T> transaction, boolean readOnly) throws DomainException {
        acquireLock(readOnly);
        InMemoryTransactionState state = instantiateTransactionState(readOnly);

        T result;
        try {
            result = transaction.execute();
            transactionState.set(Optional.empty());
        } catch (Exception e) {
            state.rollback();
            transactionState.set(Optional.empty());
            throw e;
        } finally {
            releaseLock(readOnly);
        }

        return result;
    }
}
