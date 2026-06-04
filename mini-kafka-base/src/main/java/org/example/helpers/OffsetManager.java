package org.example.helpers;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class OffsetManager {
    private final Map<String, Integer> offsets = new ConcurrentHashMap<>();

    public int getOffset(String groupId) {
        return offsets.getOrDefault(groupId, 0);
    }

    public void commitOffset(String groupId, int offset) {
        offsets.put(groupId, offset);
    }
}
