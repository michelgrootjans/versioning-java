package com.example.versioning;

public interface VersioningFactory<T> {
    VersioningRepository<T> buildRepository(String repositoryId);
}
