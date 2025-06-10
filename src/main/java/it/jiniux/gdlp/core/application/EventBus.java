package it.jiniux.gdlp.core.application;

import it.jiniux.gdlp.utility.observer.Subject;

public class EventBus extends Subject<ApplicationEvent> {
    void publish(ApplicationEvent applicationEvent) {
        this.notify(applicationEvent);
    }
}
