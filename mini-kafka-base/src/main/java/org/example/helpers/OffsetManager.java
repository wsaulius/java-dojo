package org.example.helpers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OffsetManager {
    private final Map<String, Integer> offsets = new ConcurrentHashMap<>();

    public int getOffset(String consumerId) {
        return offsets.getOrDefault(consumerId, 0);
    }

    public void commitOffset(String consumerId, int offset) {
        offsets.put(consumerId, offset);
    }
}
