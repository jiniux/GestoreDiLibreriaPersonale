package it.jiniux.gdlp.domain;

import it.jiniux.gdlp.domain.filters.EqualityFilter;
import it.jiniux.gdlp.domain.filters.Filter;
import it.jiniux.gdlp.domain.filters.OrderFilter;
import it.jiniux.gdlp.domain.filters.OrderOperator;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class FilterTest {
    @Test
    public void shouldApplyEqualityFilter() {
        Filter<Integer> filter = new EqualityFilter<>(5);

        assertTrue(filter.apply(5));
        assertFalse(filter.apply(3));
    }

    @Test
    public void shouldApplyEqualityFilterWithNull() {
        Filter<Integer> filter = new EqualityFilter<>(null);

        assertTrue(filter.apply(null));
        assertFalse(filter.apply(5));

        filter = new EqualityFilter<>(5);

        assertFalse(filter.apply(null));
        assertTrue(filter.apply(5));
    }

    @Test
    public void shouldApplyInequalityFilter() {
        Filter<Integer> filter = new OrderFilter<>(OrderOperator.GREATER_THAN, 5);

        assertTrue(filter.apply(6));
        assertFalse(filter.apply(5));

        filter = new OrderFilter<>(OrderOperator.LESS_THAN, 5);

        assertTrue(filter.apply(4));
        assertFalse(filter.apply(5));

        filter = new OrderFilter<>(OrderOperator.GREATER_THAN_OR_EQUAL_TO, 5);

        assertTrue(filter.apply(5));
        assertTrue(filter.apply(6));
        assertFalse(filter.apply(4));

        filter = new OrderFilter<>(OrderOperator.LESS_THAN_OR_EQUAL_TO, 5);

        assertTrue(filter.apply(5));
        assertTrue(filter.apply(4));
        assertFalse(filter.apply(6));
    }

    @Test
    public void shouldApplyInequalityFilterWithNull() {
        Filter<Integer> filter = new OrderFilter<>(OrderOperator.GREATER_THAN, null);

        assertFalse(filter.apply(5)); // Cannot compare with null
        assertFalse(filter.apply(null)); // Cannot compare with null

        filter = new OrderFilter<>(OrderOperator.LESS_THAN, null);

        assertFalse(filter.apply(5)); // Cannot compare with null
        assertFalse(filter.apply(null)); // Cannot compare with null

        filter = new OrderFilter<>(OrderOperator.GREATER_THAN_OR_EQUAL_TO, null);

        assertFalse(filter.apply(5)); // Cannot compare with null
        assertFalse(filter.apply(null)); // Cannot compare with null

        filter = new OrderFilter<>(OrderOperator.LESS_THAN_OR_EQUAL_TO, null);

        assertFalse(filter.apply(5)); // Cannot compare with null
        assertFalse(filter.apply(null)); // Cannot compare with null
    }

    @Test
    public void shouldApplyBinaryFilter() {
        Filter<Integer> filter = new OrderFilter<>(OrderOperator.GREATER_THAN, 5)
                .and(new OrderFilter<>(OrderOperator.LESS_THAN, 10));

        assertTrue(filter.apply(6));
        assertTrue(filter.apply(7));
        assertFalse(filter.apply(4));
        assertFalse(filter.apply(10));
        assertFalse(filter.apply(5));
    }

    @Test
    public void shouldApplyBinaryFilterWithMoreThanThreeFilters() {
        // This will result in: (A > 5) AND (A < 10) AND (A != 7)
        Filter<Integer> filter = new OrderFilter<>(OrderOperator.GREATER_THAN, 5)
                .and(new OrderFilter<>(OrderOperator.LESS_THAN, 10))
                .and(new EqualityFilter<>(7).negate());

        assertFalse(filter.apply(7));
        assertTrue(filter.apply(6));
        assertTrue(filter.apply(8));
        assertTrue(filter.apply(9));
    }

    @Test
    public void shouldApplyOrFilter() {
        // This will result in: (A < 5 OR A > 10)
        Filter<Integer> filter = new OrderFilter<>(OrderOperator.LESS_THAN, 5)
                .or(new OrderFilter<>(OrderOperator.GREATER_THAN, 10));

        assertTrue(filter.apply(4));
        assertTrue(filter.apply(11));
        assertFalse(filter.apply(6));
    }

    @Test
    public void shouldApplyOrAndFilter() {
        // This will result in: (A < 5 OR A > 10) AND (A != 3)
        Filter<Integer> filter = new OrderFilter<>(OrderOperator.LESS_THAN, 5)
                .or(new OrderFilter<>(OrderOperator.GREATER_THAN, 10))
                .and(new EqualityFilter<>(3).negate());

        assertTrue(filter.apply(1));
        assertFalse(filter.apply(7));
        assertFalse(filter.apply(3));
        assertTrue(filter.apply(11));
    }
}
