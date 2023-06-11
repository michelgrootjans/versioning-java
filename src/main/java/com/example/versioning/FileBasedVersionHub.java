package com.example.versioning;

import java.io.File;

public class FileBasedVersionHub<T> implements VersionHub<T> {
    private final File rootDirectory;
    private final Class<T> targetType;

    public FileBasedVersionHub(File rootDirectory, Class<T> targetType) {
        this.rootDirectory = rootDirectory;
        this.targetType = targetType;
    }

    @Override
    public VersioningRepository<T> buildRepository(String repositoryId) {
        return new FileBasedVersioningRepository<>(rootDirectory, targetType);
    }
}
