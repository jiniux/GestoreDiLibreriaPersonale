package it.jiniux.gdlp.presentation.javafx.common;

public interface Validable {
    /**
     * Checks if the current state is valid.
     *
     * @return true if valid, false otherwise
     */
    default boolean isValid() {
        return true;
    }

    default void validate() {
        // do nothing
    }
}
