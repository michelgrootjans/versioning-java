package com.example.versioning;

import java.io.File;

public class FileBasedVersionRepository implements VersionRepository {
    public FileBasedVersionRepository(File rootDirectory) {
    }

    @Override
    public String increment(String id) {
        return null;
    }

    @Override
    public String headOf(String id) {
        return null;
    }
}
