package it.jiniux.gdlp.domain.filters;

import lombok.Getter;

import java.util.Comparator;

public class CompareFilter<T extends Comparable<T>> implements Filter<T> {
    @Getter
    private final CompareOperator operator;

    @Getter
    private final T reference;

    @Getter
    private final Comparator<T> comparator;

    public CompareFilter(CompareOperator operator, T reference) {
        this.operator = operator;
        this.reference = reference;
        this.comparator = Comparator.naturalOrder();
    }

    public CompareFilter(CompareOperator operator, Comparator<T> comparator, T reference) {
        this.operator = operator;
        this.reference = reference;

        if (comparator == null) {
            throw new IllegalArgumentException("Comparator cannot be null");
        }

        this.comparator = comparator;
    }

    @Override
    public boolean apply(T item) {
        if (item == null) {
            return false; // null values do not satisfy the inequality condition
        }

        if (reference == null) {
            return false; // if reference is null, we cannot compare
        }

        return operator.apply(comparator, item, reference);
    }
}
