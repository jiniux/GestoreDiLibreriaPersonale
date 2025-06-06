package it.jiniux.gdlp.core.domain.filters;

public class EqualityFilter<T> implements Filter<T> {
    private final T reference;
    private final boolean negate;

    public EqualityFilter(T reference, boolean negate) {
        this.reference = reference;
        this.negate = negate;
    }

    public EqualityFilter(T reference) {
        this(reference, false);
    }

    public EqualityFilter<T> negate() {
        return new EqualityFilter<>(reference, !negate);
    }

    @Override
    public boolean apply(T item) {
        if (item == null) {
            return reference == null; // Both are null, considered equal
        }

        if (negate) {
            return !item.equals(reference);
        } else {
            return item.equals(reference);
        }
    }
}
