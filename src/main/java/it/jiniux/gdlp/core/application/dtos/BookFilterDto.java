package it.jiniux.gdlp.core.application.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookFilterDto {
    private CompositeFilterNode root;

    public enum LogicalOperator {
        AND,
        OR
    }

    public enum FilterOperator {
        EQUALS,
        NOT_EQUALS,
        CONTAINS,
        GREATER_THAN,
        GREATER_THAN_OR_EQUAL,
        LESS_THAN,
        LESS_THAN_OR_EQUAL
    }

    public enum Field {
        TITLE,
        ANY_ISBN,
        ANY_GENRE,
        ANY_AUTHOR_NAME,
        ANY_PUBLICATION_YEAR,
        ANY_PUBLISHER_NAME,
        ANY_EDITION_TITLE,
        ANY_LANGUAGE,
        READING_STATUS
    }

    @Getter
    @Setter
    public static sealed abstract class FilterNode
            permits LeafFilterNode, CompositeFilterNode
    {
        public abstract CompositeFilterNode asComposite();
        public abstract LeafFilterNode asLeaf();
    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    public static final class LeafFilterNode extends FilterNode {
        private Field field;
        private FilterOperator operator;
        private Object value;

        @Override
        public CompositeFilterNode asComposite() {
            return null;
        }

        @Override
        public LeafFilterNode asLeaf() {
            return this;
        }
    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    public static final class CompositeFilterNode extends FilterNode {
        private LogicalOperator operator = LogicalOperator.AND;
        private List<FilterNode> children = new ArrayList<>();

        public CompositeFilterNode addCriterion(Field field, FilterOperator op, Object value) {
            LeafFilterNode node = new LeafFilterNode();

            node.setField(field);
            node.setOperator(op);
            node.setValue(value);

            this.children.add(node);

            return this;
        }

        public CompositeFilterNode addGroup(CompositeFilterNode group) {
            this.children.add(group);
            return this;
        }

        @Override
        public CompositeFilterNode asComposite() {
            return this;
        }

        @Override
        public LeafFilterNode asLeaf() {
            return null;
        }
    }

    public BookFilterDto() {
        this.root = new CompositeFilterNode();
    }


    public CompositeFilterNode addComposite(CompositeFilterNode group) {
        root.addGroup(group);
        return group;
    }

    public BookFilterDto addLeaf(Field field, FilterOperator op, Object value) {
        root.addCriterion(field, op, value);
        return this;
    }

    public BookFilterDto setOperator(LogicalOperator operator) {
        root.setOperator(operator);
        return this;
    }

    public static CompositeFilterNode createGroup(LogicalOperator operator) {
        CompositeFilterNode group = new CompositeFilterNode();
        group.setOperator(operator);
        return group;
    }
}
