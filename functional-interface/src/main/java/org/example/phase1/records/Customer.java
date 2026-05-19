package org.example.phase1.records;

import java.util.List;

public record Customer(int id, List<Order> orders) {
}
