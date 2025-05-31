package it.jiniux.gdlp.domain.filters;

import lombok.Getter;

public class OrderFilter<T extends Comparable<T>> implements Filter<T> {
    @Getter
    private final OrderOperator operator;

    @Getter
    private final T reference;

    public OrderFilter(OrderOperator operator, T reference) {
        this.operator = operator;
        this.reference = reference;
    }

    @Override
    public boolean apply(T item) {
        if (item == null) {
            return false; // null values do not satisfy the inequality condition
        }

        if (reference == null) {
            return false; // if reference is null, we cannot compare
        }

        return operator.apply(item, reference);
    }
}
