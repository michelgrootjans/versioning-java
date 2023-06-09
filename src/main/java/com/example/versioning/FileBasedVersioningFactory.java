package com.example.versioning;

import java.io.File;

public class FileBasedVersioningFactory<T> implements VersioningFactory<T> {
    private final File rootDirectory;
    private Class<Planning> targetType;

    public FileBasedVersioningFactory(File rootDirectory, Class<Planning> targetType1) {
        this.rootDirectory = rootDirectory;
        targetType = targetType1;
    }

    @Override
    public VersioningRepository<T> buildRepository(String repositoryId) {
        return new FileBasedVersioningRepository<>(new File(rootDirectory, repositoryId), targetType);
    }
}
