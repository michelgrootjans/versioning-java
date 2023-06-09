package com.example.versioning;

import java.io.File;

public class FileBasedVersioningFactory<T> implements VersioningFactory<T> {
    private final File rootDirectory;

    public FileBasedVersioningFactory(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public VersioningRepository<T> buildRepository(String repositoryId) {
        return new FileBasedVersioningRepository<>(new File(rootDirectory, repositoryId));
    }
}
