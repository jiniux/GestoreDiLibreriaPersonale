package it.jiniux.gdlp.application;

import it.jiniux.gdlp.common.Subject;

public class EventBus extends Subject<Event> {
    void publish(Event event) {
        this.notify(event);
    }
}
