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

    public Versions undo() {
        var head = versions.stream()
            .filter(v -> v.hash().equals(this.head))
            .findFirst().orElseThrow();
        return new Versions(head.parenHash(), versions);
    }

    private List<Version> append(Version version) {
        return Stream.concat(
            versions.stream(),
            Stream.of(version)
        ).toList();
    }
}
