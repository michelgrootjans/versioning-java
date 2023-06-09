package com.example.versioning;

public interface VersionHub<T> {
    VersionedRepository buildRepository(String repositoryId);
}
