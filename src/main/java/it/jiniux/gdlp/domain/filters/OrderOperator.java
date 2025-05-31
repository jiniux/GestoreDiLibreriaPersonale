package it.jiniux.gdlp.domain.filters;

public enum OrderOperator {
    GREATER_THAN {
        @Override
        public <T extends Comparable<T>> boolean apply(T left, T right) {
            return left.compareTo(right) > 0;
        }
    },
    GREATER_THAN_OR_EQUAL_TO {
        @Override
        public <T extends Comparable<T>> boolean apply(T left, T right) {
            return left.compareTo(right) >= 0;
        }
    },
    LESS_THAN {
        @Override
        public <T extends Comparable<T>> boolean apply(T left, T right) {
            return left.compareTo(right) < 0;
        }
    },
    LESS_THAN_OR_EQUAL_TO {
        @Override
        public <T extends Comparable<T>> boolean apply(T left, T right) {
            return left.compareTo(right) <= 0;
        }
    };

    public <T extends Comparable<T>> boolean apply(T left, T right) {
        throw new UnsupportedOperationException("This method should be overridden by the enum constants");
    }
}
