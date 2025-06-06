package it.jiniux.gdlp.core.domain.filters;

import java.util.Optional;

public interface Filter<T> {
    boolean apply(T item);

    default Optional<CompositeFilter<T>> asCompositeFilter() {
        return Optional.empty();
    }

    default Filter<T> and(Filter<T> filter) {
        return new BinaryOperatorCompositeFilter.Builder<T>(BinaryOperator.AND)
                .addFilter(this)
                .addFilter(filter)
                .build();
    }

    default Filter<T> or(Filter<T> filter) {
        return new BinaryOperatorCompositeFilter.Builder<T>(BinaryOperator.OR)
                .addFilter(this)
                .addFilter(filter)
                .build();
    }
}
