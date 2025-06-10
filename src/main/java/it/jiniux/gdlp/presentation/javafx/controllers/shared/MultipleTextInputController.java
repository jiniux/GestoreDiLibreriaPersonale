package it.jiniux.gdlp.presentation.javafx.controllers.shared;

import it.jiniux.gdlp.presentation.javafx.common.Mediator;
import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import it.jiniux.gdlp.presentation.javafx.common.NaiveTextFieldFactory;
import it.jiniux.gdlp.presentation.javafx.common.TextFieldFactory;
import it.jiniux.gdlp.presentation.javafx.i18n.Localization;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputControl;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.layout.Region;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MultipleTextInputController implements Initializable {
    protected final Localization localization;

    @FXML private VBox container;
    @FXML private Label fieldLabel;

    private final List<TextField> textFields = new ArrayList<>();

    @Getter
    private int minimumRows = 0;

    @Getter
    private TextFieldFactory textFieldFactory = new NaiveTextFieldFactory();

    @Setter
    private Mediator<Event> mediator;

    private void notifyMediator(Event event) {
        if (mediator != null) {
            mediator.notify(event);
        }
    }

    public static abstract sealed class MultipleTextInputEvent extends Event {
        public MultipleTextInputEvent(MultipleTextInputController controller) {
            super(ANY);
            source = controller;
        }

        public static final class AddedTextField extends MultipleTextInputEvent {
            @Getter
            private final TextField textField;

            public AddedTextField(MultipleTextInputController controller, TextField textField) {
                super(controller);
                this.textField = textField;
            }
        }

        public static final class RemovedTextField extends MultipleTextInputEvent {
            @Getter
            private final TextField textField;

            public RemovedTextField(MultipleTextInputController controller, TextField textField) {
                super(controller);
                this.textField = textField;
            }
        }
    }

    public void setTextFieldFactory(TextFieldFactory textFieldFactory) {
        removeAllEntries();
        this.textFieldFactory = textFieldFactory;

        // recreate the initial rows
        setMinimumRows(minimumRows);
    }

    public MultipleTextInputController() {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.localization = serviceLocator.getLocalization();
    }

    public void handleAdd(ActionEvent actionEvent) {
        addInputRow();
    }

    public int getNumberOfEntries() {
        return textFields.size();
    }

    public void setLabelText(String labelText) {
        fieldLabel.setText(labelText);
    }

    public void setMinimumRows(int minimumRows) {
        this.minimumRows = minimumRows;
        while (textFields.size() < minimumRows) {
            addInputRow();
        }

        while (textFields.size() > minimumRows) {
            removeLastInputRow();
        }
    }

    public void removeAllEntries() {
        container.getChildren().clear();
        for (TextField textField : textFields) {
            notifyMediator(new MultipleTextInputEvent.RemovedTextField(this, textField));
        }
        textFields.clear();
    }

    public List<String> getEntries() {
        return textFields.stream().map(TextInputControl::getText).toList();
    }

    public void setEntries(Iterable<String> entries) {
        removeAllEntries();

        for (String entry : entries) {
            addInputRow();
            TextField lastTextField = textFields.getLast();
            lastTextField.setText(entry);
        }
    }

    @Getter
    private String promptText = "";

    public void setPromptText(String promptText) {
        this.promptText = promptText;
        textFields.forEach(tf -> tf.setPromptText(promptText));
    }

    private void addInputRow() {
        final TextField textField = textFieldFactory.createTextField();

        Button remove = new Button("-");

        remove.setOnAction(e -> {
            HBox parent = (HBox) remove.getParent();
            ObservableList<Node> nodes = ((VBox) parent.getParent()).getChildren();

            if (nodes.size() > minimumRows) {
                nodes.remove(parent);
                textFields.remove(textField);
                notifyMediator(new MultipleTextInputEvent.RemovedTextField(this, textField));
            }
        });

        HBox h = new HBox(10, textField, remove);
        h.setAlignment(Pos.CENTER_LEFT);

        h.setMaxWidth(Double.MAX_VALUE);
        h.setPrefWidth(Region.USE_COMPUTED_SIZE);
        HBox.setHgrow(textField, Priority.ALWAYS);

        textField.setMaxWidth(Double.MAX_VALUE);
        textField.setPrefWidth(Region.USE_COMPUTED_SIZE);

        if (promptText != null && !promptText.isEmpty()) {
            textField.setPromptText(promptText);
        }

        textFields.add(textField);
        container.getChildren().add(h);
        notifyMediator(new MultipleTextInputEvent.RemovedTextField(this, textField));
    }

    private void removeLastInputRow() {
        if (!textFields.isEmpty()) {
            TextField lastTextField = textFields.removeLast();
            HBox parent = (HBox) lastTextField.getParent();
            container.getChildren().remove(parent);
            notifyMediator(new MultipleTextInputEvent.RemovedTextField(this, lastTextField));
        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
