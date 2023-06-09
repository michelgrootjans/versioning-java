package com.example.versioning;

public interface VersioningFactory<T> {
    VersioningRepository buildRepository(String repositoryId);
}
