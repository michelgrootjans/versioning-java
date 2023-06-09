package com.example.versioning;

import java.util.ArrayList;
import java.util.List;

public record Undo(List<String> hashes) {
    public Undo() {
        this(new ArrayList<>());
    }

    public Undo add(String hash) {
        return this;
    }
}
