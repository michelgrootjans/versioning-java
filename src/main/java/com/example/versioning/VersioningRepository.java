package com.example.versioning;

public interface VersioningRepository<T> {
    void createNewVersion(Object t);

    Planning find(String planningId);

    void undo();
}
