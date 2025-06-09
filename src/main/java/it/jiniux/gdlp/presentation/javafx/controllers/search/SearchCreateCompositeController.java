package it.jiniux.gdlp.presentation.javafx.controllers.search;

import it.jiniux.gdlp.core.application.dtos.BookFilterDto;
import it.jiniux.gdlp.core.application.dtos.ReadingStatusDto;
import it.jiniux.gdlp.presentation.javafx.AlertFactory;
import it.jiniux.gdlp.presentation.javafx.AlertVariant;
import it.jiniux.gdlp.presentation.javafx.FXMLFactory;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.errors.ErrorHandler;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.Optional;

public class SearchCreateCompositeController {
    private final Localization localization;
    private final AlertFactory alertFactory;
    private final ErrorHandler errorHandler;
    private final FXMLFactory fxmlFactory;

    @FXML private Label logicalOperatorLabel;
    @FXML private ComboBox<BookFilterDto.LogicalOperator> logicalOperatorComboBox;
    @FXML private ListView<BookFilterDto.FilterNode> childrenListView;
    @FXML private Button addGroupButton;
    @FXML private Button addRuleButton;
    @FXML private Button editFilterButton;
    @FXML private Button removeFilterButton;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;


    private final ObservableList<BookFilterDto.FilterNode> childFilters = FXCollections.observableArrayList();
    private BookFilterDto.GroupNode resultNode;
    private BookFilterDto.GroupNode editingNode;

    public SearchCreateCompositeController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();

        this.localization = serviceLocator.getLocalization();
        this.alertFactory = serviceLocator.getAlertFactory();
        this.errorHandler = serviceLocator.getErrorHandler();
        this.fxmlFactory = serviceLocator.getFXMLFactory();
    }

    @FXML
    public void initialize() {
        logicalOperatorLabel.setText(localization.get(LocalizationString.FILTER_LOGICAL_OPERATOR_LABEL));
        addGroupButton.setText(localization.get(LocalizationString.ADD_GROUP_BUTTON));
        addRuleButton.setText(localization.get(LocalizationString.ADD_RULE_BUTTON));
        editFilterButton.setText(localization.get(LocalizationString.EDIT_FILTER_BUTTON));
        removeFilterButton.setText(localization.get(LocalizationString.REMOVE_FILTER_BUTTON));
        saveButton.setText(localization.get(LocalizationString.SAVE_FILTER_BUTTON));
        cancelButton.setText(localization.get(LocalizationString.CANCEL_FILTER_BUTTON));

        logicalOperatorComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(BookFilterDto.LogicalOperator operator) {
                if (operator == null) return "";
                return localization.get(LocalizationString.valueOf("LOGICAL_OP_" + operator.name()));
            }
            @Override
            public BookFilterDto.LogicalOperator fromString(String string) { 
                return null;
            }
        });
        logicalOperatorComboBox.setItems(FXCollections.observableArrayList(
                BookFilterDto.LogicalOperator.values()));
        logicalOperatorComboBox.setValue(BookFilterDto.LogicalOperator.AND);

        childrenListView.setItems(childFilters);
        childrenListView.setCellFactory(listView -> new FilterNodeCell(localization));
        
        if (editingNode != null) {
            populateFromExistingNode();
        }
    }

    public void setEditingNode(BookFilterDto.GroupNode node) {
        this.editingNode = node;
        if (logicalOperatorComboBox != null) {
            populateFromExistingNode();
        }
    }

    public void setFilter(BookFilterDto filter) {
        if (filter == null) {
            this.editingNode = new BookFilterDto.GroupNode();
        } else {
            this.editingNode = filter.getRoot();
        }
        if (logicalOperatorComboBox != null) {
            populateFromExistingNode();
        }
    }

    private void populateFromExistingNode() {
        logicalOperatorComboBox.setValue(editingNode.getOperator());
        childFilters.clear();
        childFilters.addAll(editingNode.getChildren());
    }

    private Optional<BookFilterDto.CriterionNode> showSearchCreateLeafDialog(BookFilterDto.CriterionNode nodeToEdit) {
        try {
            FXMLLoader loader = fxmlFactory.createSearchCreateLeaf();
            Parent root = loader.load();
            
            SearchCreateLeafController controller = loader.getController();
            if (nodeToEdit != null) {
                controller.setEditingNode(nodeToEdit);
            }
            
            Stage stage = new Stage();
            stage.setTitle(localization.get(LocalizationString.SEARCH_CREATE_LEAF_WINDOW_TITLE));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setResizable(false);
            
            stage.showAndWait();
            
            return controller.getResultNode();
        } catch (IOException e) {
            errorHandler.handle(e);
            return Optional.empty();
        }
    }

    public Optional<BookFilterDto.GroupNode> showSearchCreateCompositeDialog(BookFilterDto.GroupNode nodeToEdit) {
        try {
            FXMLLoader loader = fxmlFactory.createSearchCreateComposite();
            Parent root = loader.load();
            
            SearchCreateCompositeController controller = loader.getController();
            if (nodeToEdit != null) {
                controller.setEditingNode(nodeToEdit);
            }
            
            Stage stage = new Stage();
            stage.setTitle(localization.get(LocalizationString.SEARCH_CREATE_COMPOSITE_WINDOW_TITLE));
            stage.setScene(new Scene(root));
            stage.initModality(Modality.APPLICATION_MODAL);
            stage.setMinWidth(500);
            stage.setMinHeight(400);
            
            stage.showAndWait();
            
            return controller.getResultNode();
        } catch (IOException e) {

            return Optional.empty();
        }
    }

    @FXML
    private void handleAddGroup() {
        Optional<BookFilterDto.GroupNode> result = showSearchCreateCompositeDialog(null);
        result.ifPresent(childFilters::add);
    }

    @FXML
    private void handleAddRule() {
        Optional<BookFilterDto.CriterionNode> result = showSearchCreateLeafDialog(null);
        result.ifPresent(childFilters::add);
    }

    @FXML
    private void handleEditFilter() {
        BookFilterDto.FilterNode selectedNode = childrenListView.getSelectionModel().getSelectedItem();
        if (selectedNode == null) {
            alertFactory.createAlert(AlertVariant.NO_FILTER_SELECTED_FOR_EDIT).showAndWait();
            return;
        }
        
        int selectedIndex = childrenListView.getSelectionModel().getSelectedIndex();
        
        if (selectedNode instanceof BookFilterDto.GroupNode groupNode) {
            Optional<BookFilterDto.GroupNode> result = showSearchCreateCompositeDialog(groupNode);
            result.ifPresent(editedNode -> childFilters.set(selectedIndex, editedNode));
        } else if (selectedNode instanceof BookFilterDto.CriterionNode criterionNode) {
            Optional<BookFilterDto.CriterionNode> result = showSearchCreateLeafDialog(criterionNode);
            result.ifPresent(editedNode -> childFilters.set(selectedIndex, editedNode));
        }
    }

    @FXML
    private void handleRemoveFilter() {
        BookFilterDto.FilterNode selectedNode = childrenListView.getSelectionModel().getSelectedItem();
        if (selectedNode == null) {
            alertFactory.createAlert(AlertVariant.NO_FILTER_SELECTED_FOR_REMOVE).showAndWait();
            return;
        }

        Alert confirmAlert = alertFactory.createAlert(AlertVariant.CONFIRM_REMOVE_FILTER);
        Optional<ButtonType> result = confirmAlert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            childFilters.remove(selectedNode);
        }
    }

    @FXML
    private void handleSave() {
        resultNode = new BookFilterDto.GroupNode();
        resultNode.setOperator(logicalOperatorComboBox.getValue());
        resultNode.getChildren().addAll(childFilters);

        close();
    }

    @FXML
    private void handleCancel() {
        resultNode = null;
        close();
    }

    public Optional<BookFilterDto.GroupNode> getResultNode() {
        return Optional.ofNullable(resultNode);
    }

    private void close() {
        ((Stage) saveButton.getScene().getWindow()).close();
    }

    private static class FilterNodeCell extends ListCell<BookFilterDto.FilterNode> {
        private final Localization localization;
        
        public FilterNodeCell(Localization localization) {
            this.localization = localization;
        }
        
        @Override
        protected void updateItem(BookFilterDto.FilterNode item, boolean empty) {
            super.updateItem(item, empty);
            
            if (empty || item == null) {
                setText(null);
                return;
            }
            
            if (item instanceof BookFilterDto.CriterionNode criterionNode) {
                setText(formatCriterionNode(criterionNode));
            } else if (item instanceof BookFilterDto.GroupNode groupNode) {
                setText(formatGroupNode(groupNode));
            }
        }

        private String formatFilterNode(BookFilterDto.FilterNode item) {
            if (item instanceof BookFilterDto.CriterionNode criterionNode) {
                return formatCriterionNode(criterionNode);
            } else if (item instanceof BookFilterDto.GroupNode groupNode) {
                return formatGroupNode(groupNode);
            }
            return "";
        }

        private String formatCriterionNode(BookFilterDto.CriterionNode node) {
            String fieldName = localization.get(LocalizationString.valueOf("FILTER_FIELD_" + node.getField().name()));
            String operatorName = localization.get(LocalizationString.valueOf("FILTER_OPERATOR_" + node.getOperator().name()));
            
            String valueStr;
            if (node.getValue() instanceof ReadingStatusDto status) {
                valueStr = localization.get(LocalizationString.valueOf("STATUS_" + status.name()));
            } else if (node.getValue() != null) {
                valueStr = node.getValue().toString();
            } else {
                valueStr = "";
            }
            
            return fieldName + " " + operatorName + " " + valueStr;
        }
        
        private String formatGroupNode(BookFilterDto.GroupNode node) {
            String operatorName = localization.get(LocalizationString.valueOf("LOGICAL_OP_" + node.getOperator().name()));
            return String.join(" " + operatorName + " ", node.getChildren().stream().map(f -> "(" + formatFilterNode(f) + ")").toList());
        }
    }
}
