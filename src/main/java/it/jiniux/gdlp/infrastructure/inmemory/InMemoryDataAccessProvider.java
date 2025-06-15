package it.jiniux.gdlp.infrastructure.inmemory;

import it.jiniux.gdlp.core.application.DataAccessProvider;
import it.jiniux.gdlp.core.application.TransactionManager;
import it.jiniux.gdlp.core.domain.BookRepository;

public class InMemoryDataAccessProvider implements DataAccessProvider {
    private final InMemoryBookRepository bookRepository;
    private final InMemoryTransactionManager transactionManager;

    public InMemoryDataAccessProvider() {
        this.transactionManager = new InMemoryTransactionManager();
        this.bookRepository = new InMemoryBookRepository(transactionManager);
    }

    @Override
    public BookRepository getBookRepository() {
        return bookRepository;
    }

    @Override
    public TransactionManager getTransactionManager() {
        return transactionManager;
    }

    @Override
    public void gracefullyClose() {
        transactionManager.acquireReentrantLock(false);
        try {
            bookRepository.close();
        } finally {
            transactionManager.releaseReentrantLock(false);
        }
    }
}
