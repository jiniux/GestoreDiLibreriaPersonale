package it.jiniux.gdlp.core.domain.filters;

import java.util.Locale;

public class IgnoreCaseContainsFilter implements Filter<String> {
    private final String reference;

    public IgnoreCaseContainsFilter(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean apply(String item) {
        return item != null && item.toLowerCase(Locale.ROOT).contains(reference);
    }
}
