package org.example.phase7.publishers;

import org.example.phase7.interfaces.EventListener;

import java.util.ArrayList;
import java.util.List;

//Publisher
public class EventManager {

    private List<EventListener> listeners = new ArrayList<>();

    public void order(EventListener eventListener) {
        listeners.add(eventListener);
    }

    public void notifyListeners(String event){
        for (EventListener listener: listeners) {
            listener.update(event);
        }
    }
}
