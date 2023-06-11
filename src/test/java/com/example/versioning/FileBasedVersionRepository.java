package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

public class FileBasedVersionRepository implements VersionRepository {
    private final File rootDirectory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileBasedVersionRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public String increment(String id) {
        return null;
    }

    @Override
    public String headOf(String id) {
        return getVersions().map(Versions::head).orElseThrow();
    }

    @Override
    public void undo(String id) {

    }

    @Override
    public void redo(String id) {
        
    }

    private Optional<Versions> getVersions() {
        try {
            Versions versions = readFile(new File(rootDirectory, "versions.json"), Versions.class);
            return Optional.ofNullable(versions);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }

    private <T> T readFile(File file, Class<T> valueType) {
        try {
            return objectMapper.readValue(file, valueType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
