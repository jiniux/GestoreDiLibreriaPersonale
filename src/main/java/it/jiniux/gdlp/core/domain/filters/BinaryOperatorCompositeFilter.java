package it.jiniux.gdlp.core.domain.filters;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BinaryOperatorCompositeFilter<T> extends AbstractCompositeFilter<T> {
    @Getter
    private final BinaryOperator operator;


    private BinaryOperatorCompositeFilter(BinaryOperator operator, List<Filter<T>> filters) {
        if (filters.isEmpty()) {
            throw new IllegalArgumentException("At least one filter is required for a binary operator composite filter");
        }

        this.operator = operator;
        for (Filter<T> filter : filters) {
            addFilter(filter);
        }
    }

    @Override
    public void removeFilter(int index) {
        if (filters.size() <= 1) {
            throw new IllegalStateException("Cannot remove filters from a binary operator composite filter; at least one filter is required");
        }

        super.removeFilter(index);
    }

    @Override
    public boolean apply(T item) {
        Iterator<Filter<T>> iterator = filters.iterator();
        boolean result = iterator.next().apply(item);

        while (iterator.hasNext()) {
            boolean nextResult = iterator.next().apply(item);
            result = operator.apply(result, nextResult);
        }

        return result;
    }

    @Override
    public Filter<T> and(Filter<T> filter) {
        if (operator == BinaryOperator.AND) {
            if (filter == null) {
                throw new IllegalArgumentException("Filter cannot be null");
            }

            addFilter(filter);

            return this;
        } else {
            return super.and(filter);
        }
    }

    @Override
    public Filter<T> or(Filter<T> filter) {
        if (operator == BinaryOperator.OR) {
            if (filter == null) {
                throw new IllegalArgumentException("Filter cannot be null");
            }

            addFilter(filter);
            return this;
        } else {
            return super.or(filter);
        }
    }

    public static class Builder<T> {
        private final BinaryOperator operator;

        private final List<Filter<T>> filters = new ArrayList<>();

        public Builder(BinaryOperator operator) {
            this.operator = operator;
        }

        public Builder<T> addFilter(Filter<T> filter) {
            if (filter == null) {
                throw new IllegalArgumentException("Filter cannot be null");
            }

            filters.add(filter);
            return this;
        }

        public BinaryOperatorCompositeFilter<T> build() {
            return new BinaryOperatorCompositeFilter<>(operator, filters);
        }
    }
}
