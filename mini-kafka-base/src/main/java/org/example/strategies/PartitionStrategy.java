package org.example.strategies;

import org.example.models.Message;

public interface PartitionStrategy {
    int choosePartition(Message message, int partitionCount);
}
