package com.example.versioning;

public interface VersionHub<T> {
    VersioningRepository<T> buildRepository(String repositoryId);
}
