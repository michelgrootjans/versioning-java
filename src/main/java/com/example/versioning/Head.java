package com.example.versioning;

import java.util.Stack;

public record Head(String hash, Stack<String> undoStack) {
    public Head(String hash) {
        this(hash, new Stack<>());
    }
}
