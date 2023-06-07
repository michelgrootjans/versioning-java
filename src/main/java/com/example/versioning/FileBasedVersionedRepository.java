package com.example.versioning;

import java.io.File;

public class FileBasedVersionedRepository implements VersionedRepository {
    public FileBasedVersionedRepository(File rootDirectory) {
    }

    @Override
    public <T> void createNewVersion(T t) {
        
    }
}
