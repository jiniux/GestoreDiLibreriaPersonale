package it.jiniux.gdlp.presentation.javafx.common.suggestions;

import java.util.Set;

public interface SuggestionsRetriever {
    Set<String> getSuggestions(String text);
}
