package org.example.consumer;

import org.example.helpers.OffsetManager;
import org.example.models.Message;
import org.example.models.Topic;


public class Consumer {

    private final String consumerId;
    private final String groupId;
    private final Topic topic;
    private final OffsetManager offsetManager;

    public Consumer(String consumerId, String groupId, Topic topic, OffsetManager offsetManager) {
        this.consumerId = consumerId;
        this.groupId = groupId;
        this.topic = topic;
        this.offsetManager = offsetManager;
    }

    public void poll() {
        int offset = offsetManager.getOffset(groupId);
        Message message = topic.getMessage(offset);

        if (message == null) {
            return;
        }

        System.out.println(consumerId + " consumed: " + message.getPayload());

        offsetManager.commitOffset(groupId, offset + 1);
    }

}

