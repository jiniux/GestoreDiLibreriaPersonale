package it.jiniux.gdlp.domain.filters;

public class ContainsFilter implements Filter<String> {
    private final String reference;

    public ContainsFilter(String reference) {
        this.reference = reference;
    }

    @Override
    public boolean apply(String item) {
        return item != null && item.contains(reference);
    }
}
