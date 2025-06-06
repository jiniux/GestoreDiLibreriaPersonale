package it.jiniux.gdlp.core.domain.filters;

public enum BinaryOperator {
    AND {
        @Override
        public boolean apply(boolean left, boolean right) {
            return left && right;
        }
    },
    OR {
        @Override
        public boolean apply(boolean left, boolean right) {
            return left || right;
        }
    };

    public boolean apply(boolean left, boolean right) {
        throw new UnsupportedOperationException("This method should be overridden by the enum constants");
    }
}
