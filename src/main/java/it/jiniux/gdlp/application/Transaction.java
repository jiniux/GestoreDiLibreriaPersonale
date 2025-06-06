package it.jiniux.gdlp.application;

import it.jiniux.gdlp.domain.exceptions.DomainException;

public interface Transaction {
    void execute() throws DomainException;
}
