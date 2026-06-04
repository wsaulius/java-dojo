package org.example.models;

import org.example.strategies.PartitionStrategy;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Broker {

    private final Map<String, Topic> topics;
    private final PartitionStrategy partitionStrategy;

    public Broker(PartitionStrategy partitionStrategy) {
        this.topics = new ConcurrentHashMap<>();
        this.partitionStrategy = partitionStrategy;
    }


    public void createTopic(String topicName, int partitionCount) {
        topics.put(topicName, new Topic(topicName, partitionCount));
    }

    public void publish(String topicName, Message message) {
        Topic topic = topics.get(topicName);
        if (topic == null) {
            throw new RuntimeException("Topic not found: " + topicName);

        }
        int partitionId = partitionStrategy.choosePartition(message, topic.getPartitions().size());
        Partition partition = topic.getPartition(partitionId);
        partition.addMessage(message);
    }

    public Topic getTopic(String topicName) {
        return topics.get(topicName);
    }

}
