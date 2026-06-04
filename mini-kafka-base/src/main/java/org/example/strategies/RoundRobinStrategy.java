package org.example.strategies;

import org.example.models.Message;

public class RoundRobinStrategy implements PartitionStrategy{
    private int current = 0;

    //Rotates and repeats between 0 and partitionCount.
    @Override
    public synchronized int choosePartition(Message message, int partitionCount) {
        int partition = current;
        current = (current + 1) % partitionCount;

        return partition;
    }
}
