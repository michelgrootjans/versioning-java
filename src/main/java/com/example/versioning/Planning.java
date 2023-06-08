package com.example.versioning;

import java.util.ArrayList;
import java.util.List;

public record Planning(String id, String name, List<Order> orders) {
    public Planning(String name) {
        this("123", name, new ArrayList<>());
    }
    public Planning(String name, List<Order> orders) {
        this("123", name, orders);
    }
}