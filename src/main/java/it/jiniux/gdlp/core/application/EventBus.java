package it.jiniux.gdlp.core.application;

import it.jiniux.gdlp.core.common.Subject;

public class EventBus extends Subject<Event> {
    void publish(Event event) {
        this.notify(event);
    }
}
