package com.example.versioning;

public interface VersionedRepository<T> {
    void createNewVersion(Object t);

    Planning find(String planningId);

    void undo();
}
