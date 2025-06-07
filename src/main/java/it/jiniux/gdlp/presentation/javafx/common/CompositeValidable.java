package it.jiniux.gdlp.presentation.javafx.common;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CompositeValidable implements Validable {
    private final List<Validable> validables = new ArrayList<>();

    @Override
    public boolean isValid() {
        boolean valid = true;

        for (Validable validable : validables) {
            if (!validable.isValid()) {
                valid = false;
            }
        }

        return valid;
    }

    @Override
    public void validate() {
        for (Validable validable : validables) {
            validable.validate();
        }
    }

    protected void addValidable(Validable validable) {
        Objects.requireNonNull(validable);
        validables.add(validable);
    }

    protected void removeValidable(Validable validable) {
        validables.remove(validable);
    }
}
