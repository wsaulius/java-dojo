package org.example.models;

import java.util.HashMap;
import java.util.Map;

public class Broker {

    private final Map<String, Topic> topics = new HashMap<>();

    public void createTopic(String name) {
        topics.put(name, new Topic(name));
    }

    public void publish(String topicName, Message message) {
        Topic topic = topics.get(topicName);
        if(topic == null) {
            throw new RuntimeException("Topic not found");
        }
        topic.addMessage(message);
    }

    public Topic getTopic(String name) {
        return topics.get(name);
    }

}
