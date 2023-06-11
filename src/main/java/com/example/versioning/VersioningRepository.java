package com.example.versioning;

public interface VersioningRepository<T> {
    void createNewVersion(T t, String head);

    T currentVersion();

    void undo();

    void redo();
}
