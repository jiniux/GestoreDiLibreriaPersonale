package it.jiniux.gdlp.infrastructure.inmemory;

import it.jiniux.gdlp.application.DataAccessProvider;
import it.jiniux.gdlp.application.TransactionManager;
import it.jiniux.gdlp.domain.BookRepository;

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
}
