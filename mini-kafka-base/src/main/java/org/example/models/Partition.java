package org.example.models;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Partition {
    private final int partitionId;
    private final List<Message> messages = new ArrayList<>();
    private final ReentrantReadWriteLock lock = new ReentrantReadWriteLock();

    public Partition(int partitionId) {
        this.partitionId = partitionId;
    }

    public void addMessage(Message message) {
        lock.writeLock().lock();
        try {
            messages.add(message);
        } finally {
            lock.writeLock().unlock();
        }
    }

    public Message getMessage(int offset) {
        lock.readLock().lock();
        try {
            if (offset >= messages.size()) {
                return null;
            }
            return messages.get(offset);
        } finally {
            lock.readLock().unlock();
        }
    }

    public int getPartitionId() {
        return partitionId;
    }
}
