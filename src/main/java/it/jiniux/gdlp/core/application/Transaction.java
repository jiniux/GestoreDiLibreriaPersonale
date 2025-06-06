package it.jiniux.gdlp.core.application;

import it.jiniux.gdlp.core.domain.exceptions.DomainException;

public interface Transaction {
    void execute() throws DomainException;
}
