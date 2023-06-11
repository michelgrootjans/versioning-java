package com.example.versioning;

import java.io.File;

public class FileBasedVersionRepository implements VersionRepository {
    public FileBasedVersionRepository(File rootDirectory) {
    }

    @Override
    public String increment() {
        return null;
    }
}
