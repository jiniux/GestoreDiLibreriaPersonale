package it.jiniux.gdlp.domain.filters;

import java.util.*;

public abstract class AbstractCompositeFilter<T> implements CompositeFilter<T> {
    protected final List<Filter<T>> filters = new ArrayList<>();

    @Override
    public void addFilter(Filter<T> filter) {
        filters.add(filter);
    }

    @Override
    public void removeFilter(int index) {
        filters.remove(index);
    }

    @Override
    public int getFilterCount() {
        return filters.size();
    }
}
