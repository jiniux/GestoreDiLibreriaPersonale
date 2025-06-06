package it.jiniux.gdlp.core.application.mappers;

import it.jiniux.gdlp.core.application.dtos.BookFilterDto;
import it.jiniux.gdlp.core.application.dtos.BookFilterDto.FilterOperator;
import it.jiniux.gdlp.core.application.dtos.ReadingStatusDto;
import it.jiniux.gdlp.core.domain.Book;
import it.jiniux.gdlp.core.domain.ReadingStatus;
import it.jiniux.gdlp.core.domain.filters.*;
import it.jiniux.gdlp.core.domain.filters.book.BookFilter;
import it.jiniux.gdlp.core.domain.filters.book.BookFilterField;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookFilterMapper {
    private static BookFilterMapper INSTANCE;

    private static class IgnoreLowerCaseComparator implements java.util.Comparator<String> {
        private static IgnoreLowerCaseComparator INSTANCE;

        public static synchronized IgnoreLowerCaseComparator getInstance() {
            if (INSTANCE == null) {
                INSTANCE = new IgnoreLowerCaseComparator();
            }
            return INSTANCE;
        }

        @Override
        public int compare(String o1, String o2) {
            return o1.compareToIgnoreCase(o2);
        }
    }

    public static synchronized BookFilterMapper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new BookFilterMapper();
        }
        return INSTANCE;
    }

    public Filter<Book> toDomain(BookFilterDto dto) {
        if (dto == null || dto.getRoot() == null) {
            return b -> true;
        }

        return processFilterNode(dto.getRoot());
    }

    private Filter<Book> processFilterNode(BookFilterDto.FilterNode node) {
        if (node instanceof BookFilterDto.CriterionNode n) {
            return createFilterFrom(n);
        } else if (node instanceof BookFilterDto.GroupNode gn) {
            return processGroupNode(gn);
        } else {
            throw new IllegalArgumentException("Unknown filter node type: " + node.getClass().getName());
        }
    }

    private Filter<Book> processGroupNode(BookFilterDto.GroupNode groupNode) {
        List<Filter<Book>> filters = new ArrayList<>();

        for (BookFilterDto.FilterNode childNode : groupNode.getChildren()) {
            filters.add(processFilterNode(childNode));
        }

        if (filters.isEmpty()) {
            return new EmptyFilter<>();
        }

        BinaryOperator operator = groupNode.getOperator() == BookFilterDto.LogicalOperator.AND ?
                BinaryOperator.AND : BinaryOperator.OR;

        if (filters.size() == 1) {
            return filters.getFirst();
        }

        BinaryOperatorCompositeFilter.Builder<Book> builder = new BinaryOperatorCompositeFilter.Builder<>(operator);
        for (Filter<Book> filter : filters) {
            builder.addFilter(filter);
        }

        return builder.build();
    }

    private BookFilterField fieldToFilterField(BookFilterDto.Field field) {
        return switch (field) {
            case TITLE -> BookFilterField.TITLE;
            case ANY_ISBN -> BookFilterField.ANY_ISBN;
            case ANY_GENRE -> BookFilterField.ANY_GENRE;
            case ANY_PUBLISHER_NAME -> BookFilterField.ANY_PUBLISHER_NAME;
            case ANY_LANGUAGE -> BookFilterField.ANY_LANGUAGE;
            case READING_STATUS -> BookFilterField.READING_STATUS;
            case ANY_AUTHOR_NAME -> BookFilterField.ANY_AUTHOR_NAME;
            case ANY_PUBLICATION_YEAR -> BookFilterField.ANY_PUBLICATION_YEAR;
        };
    }

    private Filter<Book> createFilterFrom(BookFilterDto.CriterionNode c) {
        BookFilterField field = fieldToFilterField(c.getField());
        FilterOperator op = c.getOperator();
        Object rawValue = c.getValue();

        return switch (field) {
            case TITLE, ANY_ISBN, ANY_GENRE, ANY_PUBLISHER_NAME, ANY_LANGUAGE -> buildStringFilter(field, op, rawValue);
            case READING_STATUS -> buildReadingStatusFilter(field, op, rawValue);
            case ANY_AUTHOR_NAME -> buildAuthorNameFilter(field, op, rawValue);
            case ANY_PUBLICATION_YEAR -> buildYearFilter(field, op, rawValue);
        };
    }

    private Filter<Book> buildStringFilter(BookFilterField field, FilterOperator op, Object value) {
        if (!(value instanceof String valueString)) {
            throw new IllegalArgumentException("Value for " + field + " must be a String, but was: " + value.getClass().getName());
        }

        return switch (op) {
            case EQUALS -> new BookFilter<>(field, String.class,
                    new CompareFilter<>(CompareOperator.EQUALS_TO, IgnoreLowerCaseComparator.getInstance(), valueString));
            case NOT_EQUALS -> new BookFilter<>(field, String.class,
                    new CompareFilter<>(CompareOperator.NOT_EQUALS_TO, IgnoreLowerCaseComparator.getInstance(), valueString));
            case CONTAINS -> new BookFilter<>(field, String.class, new IgnoreCaseContainsFilter(valueString));
            default -> throw new IllegalArgumentException("Operator " + op + " not supported for String.");
        };
    }

    private Filter<Book> buildReadingStatusFilter(BookFilterField field, FilterOperator op, Object value) {
        if (!(value instanceof ReadingStatusDto dto)) {
            throw new IllegalArgumentException("Value for " + field + " must be a ReadingStatusDto, but was: " + value.getClass().getName());
        }

        ReadingStatus status = ReadingStatusMapper.getInstance().toDomain(dto);

        return switch (op) {
            case EQUALS -> new BookFilter<>(field, ReadingStatus.class, new EqualityFilter<>(status));
            case NOT_EQUALS -> new BookFilter<>(field, ReadingStatus.class, new EqualityFilter<>(status, true));
            default -> throw new IllegalArgumentException("Operator " + op + " not supported for ReadingStatus.");
        };
    }

    private Filter<Book> buildAuthorNameFilter(BookFilterField field, FilterOperator op, Object value) {
        if (!(value instanceof String valueString)) {
            throw new IllegalArgumentException("Value for " + field + " must be a String, but was: " + value.getClass().getName());
        }

        if (op == FilterOperator.CONTAINS) {
            return new BookFilter<>(field, String.class, new IgnoreCaseContainsFilter(valueString));
        }

        if (op == FilterOperator.EQUALS) {
            return new BookFilter<>(field, String.class, new EqualityFilter<>(valueString));
        }

        if (op == FilterOperator.NOT_EQUALS) {
            return new BookFilter<>(field, String.class, new EqualityFilter<>(valueString, true));
        }

        throw new IllegalArgumentException("Operator " + op + " not supported for author name.");
    }

    private Filter<Book> buildYearFilter(BookFilterField field, FilterOperator op, Object value) {
        if (value == null) {
            return new BookFilter<>(field, LocalDate.class, item -> false);
        }

        if (!(value instanceof Integer year)) {
            throw new IllegalArgumentException("Value for " + field + " must be an Integer, but was: " + value.getClass().getName());
        }

        return switch (op) {
            case EQUALS -> new BookFilter<>(field, Integer.class, new EqualityFilter<>(year));
            case NOT_EQUALS -> new BookFilter<>(field, Integer.class, new EqualityFilter<>(year, true));
            case GREATER_THAN ->
                    new BookFilter<>(field, Integer.class, new CompareFilter<>(CompareOperator.GREATER_THAN, year));
            case GREATER_THAN_OR_EQUAL ->
                    new BookFilter<>(field, Integer.class, new CompareFilter<>(CompareOperator.GREATER_THAN_OR_EQUAL_TO, year));
            case LESS_THAN -> new BookFilter<>(field, Integer.class, new CompareFilter<>(CompareOperator.LESS_THAN, year));
            case LESS_THAN_OR_EQUAL ->
                    new BookFilter<>(field, Integer.class, new CompareFilter<>(CompareOperator.LESS_THAN_OR_EQUAL_TO, year));
            default -> throw new IllegalArgumentException("Operator " + op + " not supported for year.");
        };
    }
}
