package com.example.versioning;

import java.util.List;

public record Versions(String head, List<Version> versions) {
    public Versions() {
        this("", List.of());
    }

    public Versions add(String versionHash) {
        return new Versions(versionHash, List.of());
    }
}
