package it.jiniux.gdlp.presentation.javafx.controllers.search;

import it.jiniux.gdlp.core.application.dtos.BookFilterDto;
import it.jiniux.gdlp.core.application.dtos.ReadingStatusDto;
import it.jiniux.gdlp.presentation.javafx.AlertFactory;
import it.jiniux.gdlp.presentation.javafx.AlertVariant;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import it.jiniux.gdlp.presentation.javafx.i18n.LocalizationString;
import it.jiniux.gdlp.utility.isbn.IsbnValidator;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.util.StringConverter;
import lombok.Getter;

import java.util.List;
import java.util.Optional;

public class SearchCreateLeafController {

    @FXML private Label fieldLabel;
    @FXML private ComboBox<BookFilterDto.Field> fieldComboBox;
    @FXML private Label operatorLabel;
    @FXML private ComboBox<BookFilterDto.FilterOperator> operatorComboBox;
    @FXML private Label valueLabel;
    @FXML private TextField valueTextField;
    @FXML private ComboBox<ReadingStatusDto> readingStatusComboBox;
    @FXML private Button saveButton;
    @FXML private Button cancelButton;

    private final Localization localization;
    private final AlertFactory alertFactory;

    private BookFilterDto.LeafFilterNode resultNode;
    private BookFilterDto.LeafFilterNode editingNode;

    public SearchCreateLeafController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
        this.alertFactory = serviceLocator.getAlertFactory();
    }

    @FXML
    public void initialize() {
        fieldLabel.setText(localization.get(LocalizationString.FILTER_FIELD_LABEL));
        operatorLabel.setText(localization.get(LocalizationString.FILTER_OPERATOR_LABEL));
        valueLabel.setText(localization.get(LocalizationString.FILTER_VALUE_LABEL));
        saveButton.setText(localization.get(LocalizationString.SAVE_FILTER_BUTTON));
        cancelButton.setText(localization.get(LocalizationString.CANCEL_FILTER_BUTTON));

        fieldComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(BookFilterDto.Field field) {
                if (field == null) return "";
                return localization.get(LocalizationString.valueOf("FILTER_FIELD_" + field.name()));
            }
            @Override
            public BookFilterDto.Field fromString(String string) { 
                return null;
            }
        });
        fieldComboBox.setItems(FXCollections.observableArrayList(BookFilterDto.Field.values()));

        operatorComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(BookFilterDto.FilterOperator operator) {
                if (operator == null) return "";
                return localization.get(LocalizationString.valueOf("FILTER_OPERATOR_" + operator.name()));
            }
            @Override
            public BookFilterDto.FilterOperator fromString(String string) { 
                return null;
            }
        });

        readingStatusComboBox.setConverter(new StringConverter<>() {
            @Override
            public String toString(ReadingStatusDto status) {
                if (status == null) return "";
                return localization.get(LocalizationString.valueOf("STATUS_" + status.name()));
            }
            @Override
            public ReadingStatusDto fromString(String string) { 
                return null;
            }
        });
        readingStatusComboBox.setItems(FXCollections.observableArrayList(ReadingStatusDto.values()));
        readingStatusComboBox.setVisible(false);
        
        fieldComboBox.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal != null) {
                updateOperatorsForField(newVal);
                updateValueControlForField(newVal);
            }
        });

        if (editingNode != null) {
            populateFromExistingNode();
        }
    }

    public void setEditingNode(BookFilterDto.LeafFilterNode node) {
        this.editingNode = node;
        if (fieldComboBox != null) {
            populateFromExistingNode();
        }
    }

    private void populateFromExistingNode() {
        fieldComboBox.setValue(editingNode.getField());
        operatorComboBox.setValue(editingNode.getOperator());
        
        if (editingNode.getField() == BookFilterDto.Field.READING_STATUS && editingNode.getValue() instanceof ReadingStatusDto) {
            readingStatusComboBox.setValue((ReadingStatusDto) editingNode.getValue());
        } else if (editingNode.getValue() != null) {
            valueTextField.setText(editingNode.getValue().toString());
        }
    }

    private void updateOperatorsForField(BookFilterDto.Field field) {
        List<BookFilterDto.FilterOperator> operators = getSupportedOperators(field);
        operatorComboBox.setItems(FXCollections.observableArrayList(operators));
        if (!operators.isEmpty()) {
            operatorComboBox.setValue(operators.getFirst());
        }
    }

    private void updateValueControlForField(BookFilterDto.Field field) {
        if (field == BookFilterDto.Field.READING_STATUS) {
            valueTextField.setVisible(false);
            valueTextField.setManaged(false);
            readingStatusComboBox.setVisible(true);
            readingStatusComboBox.setManaged(true);
        } else {
            valueTextField.setVisible(true);
            valueTextField.setManaged(true);
            readingStatusComboBox.setVisible(false);
            readingStatusComboBox.setManaged(false);
        }
    }

    private List<BookFilterDto.FilterOperator> getSupportedOperators(BookFilterDto.Field field) {
        return switch (field) {
            case ANY_EDITION_TITLE, TITLE, ANY_ISBN, ANY_GENRE, ANY_AUTHOR_NAME, ANY_PUBLISHER_NAME, ANY_LANGUAGE ->
                List.of(BookFilterDto.FilterOperator.EQUALS, 
                        BookFilterDto.FilterOperator.NOT_EQUALS, 
                        BookFilterDto.FilterOperator.CONTAINS);

            case ANY_PUBLICATION_YEAR ->
                List.of(BookFilterDto.FilterOperator.EQUALS,
                        BookFilterDto.FilterOperator.NOT_EQUALS,
                        BookFilterDto.FilterOperator.GREATER_THAN,
                        BookFilterDto.FilterOperator.GREATER_THAN_OR_EQUAL,
                        BookFilterDto.FilterOperator.LESS_THAN,
                        BookFilterDto.FilterOperator.LESS_THAN_OR_EQUAL);

            case READING_STATUS ->
                List.of(BookFilterDto.FilterOperator.EQUALS,
                        BookFilterDto.FilterOperator.NOT_EQUALS);
        };
    }

    @FXML
    private void handleSave() {
        BookFilterDto.Field field = fieldComboBox.getValue();
        BookFilterDto.FilterOperator operator = operatorComboBox.getValue();

        if (field == null) {
            alertFactory.createAlert(AlertVariant.INVALID_FILTER_FIELD_EMPTY).showAndWait();
            return;
        }
        
        if (operator == null) {
            alertFactory.createAlert(AlertVariant.INVALID_FILTER_OPERATOR_EMPTY).showAndWait();
            return;
        }

        Object value = null;
        
        try {
            if (field == BookFilterDto.Field.READING_STATUS) {
                value = readingStatusComboBox.getValue();
                if (value == null) {
                    alertFactory.createAlert(AlertVariant.INVALID_FILTER_VALUE_EMPTY).showAndWait();
                    return;
                }
            } else {
                String stringValue = valueTextField.getText();
                if (stringValue == null || stringValue.isBlank()) {
                    if (operator != BookFilterDto.FilterOperator.CONTAINS) {
                        alertFactory.createAlert(AlertVariant.INVALID_FILTER_VALUE_EMPTY).showAndWait();
                        return;
                    }
                }
                
                value = parseValueForField(field, stringValue);
            }
        } catch (NumberFormatException e) {
            String fieldName = localization.get(LocalizationString.valueOf("FILTER_FIELD_" + field.name()));
            alertFactory.createAlert(AlertVariant.INVALID_FILTER_VALUE_TYPE, fieldName, "Integer").showAndWait();
            return;
        } catch (InvalidIsbnException e) {
            String fieldName = localization.get(LocalizationString.valueOf("FILTER_FIELD_" + field.name()));
            alertFactory.createAlert(AlertVariant.INVALID_FILTER_VALUE_TYPE, e.getIsbn(), fieldName).showAndWait();
            return;
        }

        resultNode = new BookFilterDto.LeafFilterNode();
        resultNode.setField(field);
        resultNode.setOperator(operator);
        resultNode.setValue(value);

        closeStage();
    }

    private static class InvalidIsbnException extends RuntimeException {
        @Getter
        private final String isbn;

        public InvalidIsbnException(String isbn) {
            super("Invalid ISBN format.");
            this.isbn = isbn;
        }
    }

    private Object parseValueForField(BookFilterDto.Field field, String stringValue) {
        if (stringValue == null || stringValue.isBlank()) {
            return stringValue;
        }


        return switch (field) {
            case ANY_PUBLICATION_YEAR -> Integer.parseInt(stringValue);
            case ANY_ISBN -> {
                String stripped = stringValue.replace("-", "").trim();

                if (IsbnValidator.isValidIsbn10(stripped)) {
                    yield stripped;
                } else if (IsbnValidator.isValidIsbn13(stripped)) {
                    yield stripped;
                } else {
                    throw new InvalidIsbnException(stripped);
                }
            }
            default -> stringValue;
        };
    }

    @FXML
    private void handleCancel() {
        resultNode = null;
        closeStage();
    }

    public Optional<BookFilterDto.LeafFilterNode> getResultNode() {
        return Optional.ofNullable(resultNode);
    }

    private void closeStage() {
        ((Stage) saveButton.getScene().getWindow()).close();
    }
}
