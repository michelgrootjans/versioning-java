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
    public Optional<Versions> find(String id) {
        return getVersions();
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
            Versions result;
            try {
                result = objectMapper.readValue(new File(rootDirectory, "versions.json"), Versions.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            Versions versions = result;
            return Optional.ofNullable(versions);
        } catch (RuntimeException e) {
            return Optional.empty();
        }
    }
}
