package it.jiniux.gdlp.application.mappers;

import it.jiniux.gdlp.application.dtos.BookFilterDto;
import it.jiniux.gdlp.application.dtos.BookFilterDto.FilterOperator;
import it.jiniux.gdlp.application.dtos.ReadingStatusDto;
import it.jiniux.gdlp.domain.Book;
import it.jiniux.gdlp.domain.ReadingStatus;
import it.jiniux.gdlp.domain.filters.*;
import it.jiniux.gdlp.domain.filters.book.BookFilter;
import it.jiniux.gdlp.domain.filters.book.BookFilterField;

import java.time.LocalDate;
import java.time.chrono.ChronoLocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookFilterMapper {
    private static BookFilterMapper INSTANCE;

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

    private Filter<Book> createFilterFrom(BookFilterDto.CriterionNode c) {
        BookFilterField field = BookFilterField.valueOf(c.getField());
        FilterOperator op = c.getOperator();
        String rawValue = c.getValue();

        return switch (field) {
            case TITLE, ANY_ISBN, ANY_GENRE, ANY_PUBLISHER_NAME, ANY_LANGUAGE -> buildStringFilter(field, op, rawValue);
            case READING_STATUS -> buildReadingStatusFilter(field, op, rawValue);
            case ANY_AUTHOR_NAME -> buildAuthorNameFilter(field, op, rawValue);
            case ANY_PUBLICATION_YEAR -> buildYearFilter(field, op, rawValue);
        };
    }

    private Filter<Book> buildStringFilter(BookFilterField field, FilterOperator op, String value) {
        if (value == null) {
            return new BookFilter<>(field, String.class, item -> false);
        }

        return switch (op) {
            case EQUALS -> new BookFilter<>(field, String.class, new EqualityFilter<>(value));
            case NOT_EQUALS -> new BookFilter<>(field, String.class, new EqualityFilter<>(value, true));
            case CONTAINS -> new BookFilter<>(field, String.class, s -> s != null && s.contains(value));
            default -> throw new IllegalArgumentException("Operator " + op + " not supported for String.");
        };
    }

    private Filter<Book> buildReadingStatusFilter(BookFilterField field, FilterOperator op, String value) {
        ReadingStatusDto dto = ReadingStatusDto.valueOf(value);
        ReadingStatus status = ReadingStatusMapper.getInstance().toDomain(dto);

        return switch (op) {
            case EQUALS -> new BookFilter<>(field, ReadingStatus.class, new EqualityFilter<>(status));
            case NOT_EQUALS -> new BookFilter<>(field, ReadingStatus.class, new EqualityFilter<>(status, true));
            default -> throw new IllegalArgumentException("Operator " + op + " not supported for ReadingStatus.");
        };
    }

    private Filter<Book> buildAuthorNameFilter(BookFilterField field, FilterOperator op, String value) {
        if (op == FilterOperator.CONTAINS && value != null) {
            return new BookFilter<>(field, String.class, new ContainsFilter(value));
        }

        if (op == FilterOperator.EQUALS && value != null) {
            return new BookFilter<>(field, String.class, new EqualityFilter<>(value));
        }

        if (op == FilterOperator.NOT_EQUALS && value != null) {
            return new BookFilter<>(field, String.class, new EqualityFilter<>(value, true));
        }

        throw new IllegalArgumentException("Operator " + op + " not supported for author name.");
    }

    private Filter<Book> buildYearFilter(BookFilterField field, FilterOperator op, String value) {
        if (value == null) {
            return new BookFilter<>(field, LocalDate.class, item -> false);
        }
        LocalDate year = LocalDate.parse(value);

        return switch (op) {
            case EQUALS -> new BookFilter<>(field, ChronoLocalDate.class, new EqualityFilter<>(year));
            case NOT_EQUALS -> new BookFilter<>(field, ChronoLocalDate.class, new EqualityFilter<>(year, true));
            case GREATER_THAN ->
                    new BookFilter<>(field, ChronoLocalDate.class, new OrderFilter<>(OrderOperator.GREATER_THAN, year));
            case GREATER_THAN_OR_EQUAL ->
                    new BookFilter<>(field, ChronoLocalDate.class, new OrderFilter<>(OrderOperator.GREATER_THAN_OR_EQUAL_TO, year));
            case LESS_THAN -> new BookFilter<>(field, ChronoLocalDate.class, new OrderFilter<>(OrderOperator.LESS_THAN, year));
            case LESS_THAN_OR_EQUAL ->
                    new BookFilter<>(field, ChronoLocalDate.class, new OrderFilter<>(OrderOperator.LESS_THAN_OR_EQUAL_TO, year));
            default -> throw new IllegalArgumentException("Operator " + op + " not supported for year.");
        };
    }
}
