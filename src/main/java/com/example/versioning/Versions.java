package com.example.versioning;

import java.util.List;
import java.util.stream.Stream;

public record Versions(String head, List<Version> versions) {
    public Versions(String root) {
        this(root, List.of(new Version(root, "")));
    }

    public Versions push(String versionHash) {
        return new Versions(versionHash, append(new Version(versionHash, head)));
    }

    public Versions undo() {
        var head = versions.stream()
            .filter(v -> v.hash().equals(this.head))
            .findFirst().orElseThrow();
        return new Versions(head.parenHash(), versions);
    }

    public Versions redo() {
        var newVersion = versions.stream()
            .filter(v -> v.parenHash().equals(this.head))
            .findFirst().orElseThrow();
        return new Versions(newVersion.hash(), versions);
    }

    private List<Version> append(Version version) {
        return Stream.concat(
            versions.stream(),
            Stream.of(version)
        ).toList();
    }
}
