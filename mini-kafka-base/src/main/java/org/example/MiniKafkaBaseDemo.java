package org.example;

import org.example.consumer.Consumer;
import org.example.helpers.OffsetManager;
import org.example.models.Broker;
import org.example.models.Message;
import org.example.models.Partition;
import org.example.models.Topic;
import org.example.producer.Producer;
import org.example.strategies.RoundRobinStrategy;

public class MiniKafkaBaseDemo {
    public static void main(String[] args) throws InterruptedException {
        Broker broker = new Broker(new RoundRobinStrategy());

        broker.createTopic("orders", 2);

        for (int i = 1; i <= 10; i++) {
            broker.publish("orders", new Message("Order " + i));
        }

        Topic topic = broker.getTopic("orders");

        for(Partition partition : topic.getPartitions()) {

            System.out.println("Partition " + partition.getPartitionId());

            for (int offset = 0; ; offset++) {
                Message message = partition.getMessage(offset);

                if(message == null) {
                    break;
                }
                System.out.println(message.getPayload());
            }
            System.out.println();
        }
    }
}