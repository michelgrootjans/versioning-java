package com.example.versioning;

import java.io.File;

public class FileBasedVersionHub<T> implements VersionHub {
    private final File rootDirectory;

    public FileBasedVersionHub(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public VersionedRepository buildRepository(String repositoryId) {
        return new FileBasedVersionedRepository(new File(rootDirectory, repositoryId));
    }
}
