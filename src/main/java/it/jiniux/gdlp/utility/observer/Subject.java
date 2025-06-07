package it.jiniux.gdlp.utility.observer;

import java.util.List;

public class Subject<E> {
    private final List<Observer<E>> observers;

    public Subject() {
        this.observers = new java.util.ArrayList<>();
    }

    public void attach(Observer<E> observer) {
        if (observers.contains(observer)) {
            throw new IllegalArgumentException("observer already added");
        }

        observers.add(observer);
    }

    public void detach(Observer<E> observer) {
        if (!observers.contains(observer)) {
            throw new IllegalArgumentException("observer not found");
        }

        observers.remove(observer);
    }

    public void notify(E event) {
        for (Observer<E> observer : observers) {
            observer.update(event);
        }
    }
}
