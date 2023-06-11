package com.example.versioning;

import static com.example.versioning.ListAppender.append;

import java.util.List;

public record Versions(String head, List<Version> versions) {
    public Versions(String root) {
        this(root, List.of(new Version(root, "")));
    }

    public Versions push(String newHead) {
        List<Version> versions = append(new Version(newHead, head), this.versions);
        return new Versions(newHead, versions);
    }

    public Versions undo() {
        var head = versions.stream()
            .filter(v -> v.head().equals(this.head))
            .findFirst().orElseThrow();
        return new Versions(head.parent(), versions);
    }

    public Versions redo() {
        var newVersion = versions.stream()
            .filter(v -> v.parent().equals(this.head))
            .findFirst().orElseThrow();
        return new Versions(newVersion.head(), versions);
    }
}
