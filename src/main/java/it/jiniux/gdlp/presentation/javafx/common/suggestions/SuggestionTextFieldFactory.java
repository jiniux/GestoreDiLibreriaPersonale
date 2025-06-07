package it.jiniux.gdlp.presentation.javafx.common.suggestions;

import it.jiniux.gdlp.presentation.javafx.common.TextFieldFactory;
import javafx.scene.control.TextField;


public class SuggestionTextFieldFactory implements TextFieldFactory  {
    private final SuggestionsRetriever suggestionsRetriever;
    private final int debounceDelayMs;

    public SuggestionTextFieldFactory(SuggestionsRetriever suggestionsRetriever, int debounceDelayMs) {
        this.suggestionsRetriever = suggestionsRetriever;
        this.debounceDelayMs = debounceDelayMs;
    }

    @Override
    public TextField createTextField() {
        TextField textField = new TextField();
        textField.textProperty().addListener(new SuggestionsContextMenu(debounceDelayMs, textField, suggestionsRetriever));

        return textField;
    }
}
