package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Topic {
    private final String name;
    private final List<Partition> partitions = new ArrayList<>();


    public Topic(String name, int partitionCount) {
        this.name = name;

        for (int i = 0; i < partitionCount; i++) {
            partitions.add(new Partition(i));
        }
    }

    public Partition getPartition(int id) {
        return partitions.get(id);
    }

    public List<Partition> getPartitions() {
        return partitions;
    }

}

