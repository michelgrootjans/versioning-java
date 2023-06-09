package com.example.versioning;

import java.io.File;

public class FileBasedVersioningFactory<T> implements VersioningFactory<T> {
    private final File rootDirectory;
    private final Class<T> targetType;

    public FileBasedVersioningFactory(File rootDirectory, Class<T> targetType) {
        this.rootDirectory = rootDirectory;
        this.targetType = targetType;
    }

    @Override
    public VersioningRepository<T> buildRepository(String repositoryId) {
        return new FileBasedVersioningRepository<>(new File(rootDirectory, repositoryId), targetType);
    }
}
