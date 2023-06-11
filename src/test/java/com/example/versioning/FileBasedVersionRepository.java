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
        try {
            Versions result = objectMapper.readValue(new File(rootDirectory, "versions.json"), Versions.class);
            return Optional.ofNullable(result);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(String planningId, Versions version) {
        try {
            objectMapper.writeValue(new File(rootDirectory, "versions.json"), version);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
