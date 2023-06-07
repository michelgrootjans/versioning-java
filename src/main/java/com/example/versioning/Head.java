package com.example.versioning;

public record Head(String hash, String parent) {
    public Head undo() {
        return new Head(parent, "");
    }
}
