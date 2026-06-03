package org.example.models;

import java.util.ArrayList;
import java.util.List;

public class Topic {
    private final String name;
    private final List<Message> messages;

    public Topic(String name) {
        this.name = name;
        this.messages = new ArrayList<>();
    }

    public void addMessage(Message message) {
        messages.add(message);
    }

    public List<Message> getMessages() {
        return messages;
    }
}
