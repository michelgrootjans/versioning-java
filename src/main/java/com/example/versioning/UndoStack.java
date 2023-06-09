package com.example.versioning;

import java.util.Stack;

public record UndoStack(Stack<String> hashes) {
    public UndoStack() {
        this(new Stack<>());
    }

    public UndoStack push(String hash) {
        this.hashes.push(hash);
        return new UndoStack(hashes);
    }
}
