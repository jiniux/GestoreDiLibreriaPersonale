package it.jiniux.gdlp.core.domain.filters;

public class EmptyFilter<R> implements Filter<R> {
    @Override
    public boolean apply(Object item) {
        return true;
    }
}
