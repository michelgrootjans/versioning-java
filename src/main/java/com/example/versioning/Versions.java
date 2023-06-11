package com.example.versioning;

import java.util.List;
import java.util.stream.Stream;

public record Versions(String head, List<Version> versions) {
    public Versions() {
        this("", List.of());
    }

    public Versions add(String versionHash) {
        Stream<Version> stream = versions.stream();
        Stream<Version> versionHash1 = Stream.of(new Version());
        List<Version> list = Stream.concat(stream, versionHash1).toList();
        return new Versions(versionHash, list);
    }
}
