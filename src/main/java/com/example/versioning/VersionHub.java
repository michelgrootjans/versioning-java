package com.example.versioning;

public interface VersionHub {
    VersionedRepository buildRepository(String repositoryId);
}
