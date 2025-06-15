package it.jiniux.gdlp.core.application;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
public class Page<T> {
    private final int number;
    private final long size;
    private final int totalPages;
    private final List<T> elements;

    public List<T> getElements() {
        return Collections.unmodifiableList(elements);
    }

    public Page(int number, int limit, List<T> elements, int totalElements) {
        this(number, limit, (int)Math.ceil((double)totalElements / limit), elements);
    }

    public Page(int number, int size, int totalPages, List<T> elements) {
        if (number < 0 || size <= 0 || totalPages < 0) {
            throw new IllegalArgumentException("Invalid page parameters");
        }

        this.number = number;
        this.size = size;
        this.totalPages = totalPages;
        this.elements = new ArrayList<>(elements);
    }

    public static class Builder {
        private int number;
        private int size;
        private int totalPages;
        private List<Object> elements = new ArrayList<>();

        public Builder number(int number) {
            this.number = number;
            return this;
        }

        public Builder size(int size) {
            this.size = size;
            return this;
        }

        public Builder totalPages(int totalPages) {
            this.totalPages = totalPages;
            return this;
        }

        public Builder elements(List<Object> elements) {
            this.elements = new ArrayList<>(elements);
            return this;
        }

        public Page<Object> build() {
            return new Page<>(number, size, totalPages, elements);
        }
    }
}
