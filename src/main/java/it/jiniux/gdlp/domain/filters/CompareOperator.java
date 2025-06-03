package it.jiniux.gdlp.domain.filters;

import java.util.Comparator;

public enum CompareOperator {
    GREATER_THAN {
        @Override
        public <T extends Comparable<T>> boolean apply(Comparator<T> comparator, T left, T right) {
            return comparator.compare(left, right) > 0;
        }
    },
    GREATER_THAN_OR_EQUAL_TO {
        @Override
        public <T extends Comparable<T>> boolean apply(Comparator<T> comparator, T left, T right) {
            return comparator.compare(left, right) >= 0;
        }
    },
    LESS_THAN {
        @Override
        public <T extends Comparable<T>> boolean apply(Comparator<T> comparator, T left, T right) {
            return comparator.compare(left, right) < 0;
        }
    },
    LESS_THAN_OR_EQUAL_TO {
        @Override
        public <T extends Comparable<T>> boolean apply(Comparator<T> comparator, T left, T right) {
            return comparator.compare(left, right) <= 0;
        }
    },
    EQUALS_TO {
        @Override
        public <T extends Comparable<T>> boolean apply(Comparator<T> comparator, T left, T right) {
            return comparator.compare(left, right) == 0;
        }
    },
    NOT_EQUALS_TO {;
        @Override
        public <T extends Comparable<T>> boolean apply(Comparator<T> comparator, T left, T right) {
            return comparator.compare(left, right) != 0;
        }
    };

    public <T extends Comparable<T>> boolean apply(Comparator<T> comparator, T left, T right) {
        throw new UnsupportedOperationException("This method should be overridden by the enum constants");
    }
}
