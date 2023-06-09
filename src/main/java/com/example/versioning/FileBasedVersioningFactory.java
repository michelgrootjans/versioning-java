package com.example.versioning;

import java.io.File;

public class FileBasedVersioningFactory<T> implements VersioningFactory<T> {
    private final File rootDirectory;
    private Class<T> targetType;

    public FileBasedVersioningFactory(File rootDirectory, Class<Planning> targetType) {
        this.rootDirectory = rootDirectory;
        this.targetType = (Class<T>) targetType;
    }

    @Override
    public VersioningRepository<T> buildRepository(String repositoryId) {
        return new FileBasedVersioningRepository<T>(new File(rootDirectory, repositoryId), targetType);
    }
}
