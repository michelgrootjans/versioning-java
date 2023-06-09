package com.example.versioning;

public interface VersioningRepository<T> {
    void createNewVersion(T t);

    Planning find(String planningId);

    void undo();
}
