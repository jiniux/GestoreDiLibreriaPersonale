package it.jiniux.gdlp.application.dtos;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
public class BookFilterDto {
    private FilterNode root;

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
        ANY_LANGUAGE,
        READING_STATUS
    }

    @Getter
    @Setter
    public static sealed abstract class FilterNode
            permits CriterionNode, GroupNode
    { }

    @EqualsAndHashCode(callSuper = false)
    @Data
    public static final class CriterionNode extends FilterNode {
        private Field field;
        private FilterOperator operator;
        private Object value;
    }

    @EqualsAndHashCode(callSuper = false)
    @Data
    public static final class GroupNode extends FilterNode {
        private LogicalOperator operator = LogicalOperator.AND;
        private List<FilterNode> children = new ArrayList<>();

        public GroupNode addCriterion(Field field, FilterOperator op, Object value) {
            CriterionNode node = new CriterionNode();

            node.setField(field);
            node.setOperator(op);
            node.setValue(value);

            this.children.add(node);

            return this;
        }

        public GroupNode addGroup(GroupNode group) {
            this.children.add(group);
            return this;
        }
    }

    public BookFilterDto() {
        this.root = new GroupNode();
    }

    public GroupNode getRoot() {
        if (root == null) {
            root = new GroupNode();
        }

        // Used to guarantee that the root is always a GroupNode
        if (!(root instanceof GroupNode)) {
            GroupNode newRoot = new GroupNode();
            newRoot.getChildren().add(root);
            root = newRoot;
        }

        return (GroupNode) root;
    }

    public GroupNode addGroup(GroupNode group) {
        getRoot().addGroup(group);
        return group;
    }

    public BookFilterDto addCriterion(Field field, FilterOperator op, Object value) {
        getRoot().addCriterion(field, op, value);
        return this;
    }

    public BookFilterDto setOperator(LogicalOperator operator) {
        getRoot().setOperator(operator);
        return this;
    }

    public static GroupNode createGroup(LogicalOperator operator) {
        GroupNode group = new GroupNode();
        group.setOperator(operator);
        return group;
    }
}
