package com.example.versioning;

import java.util.ArrayList;
import java.util.List;

public record Planning(String id, List<Order> orders) {
    public Planning(String id) {
        this(id, new ArrayList<>());
    }

    public void add(Order order) {
        orders.add(order);
    }
}