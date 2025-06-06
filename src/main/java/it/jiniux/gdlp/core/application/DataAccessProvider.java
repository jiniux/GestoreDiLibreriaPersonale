package it.jiniux.gdlp.core.application;

import it.jiniux.gdlp.core.domain.BookRepository;

public interface DataAccessProvider {
    BookRepository getBookRepository();
    TransactionManager getTransactionManager();
}
