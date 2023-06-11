package com.example.versioning;

public interface VersionRepository {
    String increment(String id);

    String headOf(String id);

    void undo(String id);
}
