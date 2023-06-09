package com.example.versioning;

public interface VersioningRepository<T> {
    void createNewVersion(T t);

    T currentVersion();

    void undo();
}
