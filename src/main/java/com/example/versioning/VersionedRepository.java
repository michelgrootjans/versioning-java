package com.example.versioning;

public interface VersionedRepository {
    void createNewVersion(Object t);

    Planning find(String planningId);
}
