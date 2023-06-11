package com.example.versioning;

import java.util.List;
import java.util.stream.Stream;

public record Versions(String head, List<Version> versions) {
    public Versions(String rootHash) {
        this(rootHash, List.of(new Version(rootHash, "")));
    }

    public Versions add(String versionHash) {
        return new Versions(versionHash, append(new Version(versionHash, head)));
    }

    private List<Version> append(Version version) {
        return Stream.concat(
            versions.stream(),
            Stream.of(version)
        ).toList();
    }
}
