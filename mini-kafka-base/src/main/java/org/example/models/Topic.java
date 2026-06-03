package org.example.models;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class Topic {
    private final String name;
    private final BlockingQueue<Message> messages = new LinkedBlockingQueue<>();

    public Topic(String name) {
        this.name = name;
    }

    public void addMessage(Message message) {
        try {
            messages.put(message);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public Message getMessage() {
        try {
            return messages.take();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            return null;
        }
    }
}
