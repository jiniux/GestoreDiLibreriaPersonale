package it.jiniux.gdlp.presentation.javafx.common.suggestions;

import it.jiniux.gdlp.presentation.javafx.ServiceLocator;
import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.TextField;
import javafx.util.Duration;
import lombok.Getter;

import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class SuggestionsContextMenu implements ChangeListener<String> {
    @Getter
    private final int debounceDelayMs;

    private final SuggestionsRetriever suggestionsRetriever;

    private Future<?> suggestionsFuture = null;
    private final PauseTransition debounce;

    ContextMenu suggestionsPopup = new ContextMenu();

    private final TextField textField;

    private final ExecutorService executorService;

    public SuggestionsContextMenu(int debounceDelayMs, TextField textField, SuggestionsRetriever suggestionsRetriever) {
        ServiceLocator serviceLocator = ServiceLocator.getInstance();
        this.executorService = serviceLocator.getExecutorService();

        this.debounceDelayMs = debounceDelayMs;
        this.debounce = new PauseTransition(Duration.millis(debounceDelayMs));
        this.suggestionsRetriever = suggestionsRetriever;
        this.textField = textField;
    }

    public void hide() {
        suggestionsPopup.hide();
        
        if (suggestionsFuture != null && !suggestionsFuture.isDone()) {
            suggestionsFuture.cancel(true);
        }
    }

    private String lastSetText = "";

    public void showSuggestions() {
        debounce.setOnFinished(_ -> {
            if (suggestionsFuture != null && !suggestionsFuture.isDone()) {
                suggestionsFuture.cancel(true);
            }

            suggestionsFuture = executorService.submit(() -> {
                Set<String> suggestions = suggestionsRetriever.getSuggestions(textField.getText());

                if (Thread.currentThread().isInterrupted()) {
                    return;
                }

                Platform.runLater(() -> {
                    suggestionsPopup.getItems().clear();

                    suggestions.forEach(author -> {
                        var menuItem = new javafx.scene.control.MenuItem(author);
                        menuItem.setOnAction(event -> {
                            textField.setText(author);
                            lastSetText = author;
                        });
                        suggestionsPopup.getItems().add(menuItem);
                    });

                    boolean anyMatches = suggestions.stream()
                            .anyMatch(s -> s.equalsIgnoreCase(textField.getText().trim()));

                    if (anyMatches) {
                        suggestionsPopup.hide();
                        return;
                    }

                    if (!suggestions.isEmpty() || suggestions.contains(textField.getText().trim())) {
                        suggestionsPopup.show(textField, Side.BOTTOM, 0, 0);
                    } else {
                        suggestionsPopup.hide();
                    }
                });
            });
        });

        debounce.playFromStart();
    }

    @Override
    public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
        if (newValue == null || newValue.isBlank()) {
            hide();
            return;
        }

        if (lastSetText.equals(newValue)) {
            suggestionsPopup.hide();
            return;
        }

        if (suggestionsPopup.isShowing()) {
            suggestionsPopup.hide();
            return;
        }

        showSuggestions();
    }
}
