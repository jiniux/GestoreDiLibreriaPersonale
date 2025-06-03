package it.jiniux.gdlp.domain.filters.book;

import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.filters.Filter;

import java.util.Iterator;

public class BookFilter<R> implements Filter<Book> {
    private final BookFilterField field;

    private final Filter<R> fieldFilter;

    public BookFilter(BookFilterField field, Class<R> filterClass, Filter<R> fieldFilter) {
        this.field = field;
        this.fieldFilter = fieldFilter;

        if (!field.supportsReferenceClass(filterClass)) {
            throw new IllegalArgumentException("Field " + field + " does not support reference class " + filterClass.getName());
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public boolean apply(Book item) {
        if (item == null) return false;

        // Cast is unchecked, but we assume the field extractor is compatible with the type R.
        // This check is carried out in the constructor.
        Iterator<R> iterator = (Iterator<R>) field.createIterator(item);

        while (iterator.hasNext()) {
            R value = iterator.next();

            if (fieldFilter.apply(value)) {
                return true;
            }
        }

        return false;
    }
}
