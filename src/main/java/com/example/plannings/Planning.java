package com.example.plannings;

import java.util.ArrayList;
import java.util.List;

public record Planning(String name, List<Order> orders) {
    public Planning(String name) {
        this(name, new ArrayList<>());
    }
}