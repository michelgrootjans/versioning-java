package com.example.versioning;

import java.util.ArrayList;
import java.util.List;

public record Planning(String name, List<Order> orders) {
    public Planning(String name) {
        this(name, new ArrayList<>());
    }

    public void add(Order order) {
        orders.add(order);
    }
}