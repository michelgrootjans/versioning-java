package com.example.versioning;

public record Versions(String head) {
    public Versions() {
        this("");
    }

    public Versions add(String versionHash) {
        return new Versions(head);
    }
}
