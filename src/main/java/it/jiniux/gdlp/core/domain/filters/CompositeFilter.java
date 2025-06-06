package it.jiniux.gdlp.core.domain.filters;

import java.util.Optional;

public interface CompositeFilter<T> extends Filter<T> {
    void addFilter(Filter<T> filter);
    void removeFilter(int index);
    int getFilterCount();

    @Override
    default Optional<CompositeFilter<T>> asCompositeFilter() {
        return Optional.of(this);
    }
}
