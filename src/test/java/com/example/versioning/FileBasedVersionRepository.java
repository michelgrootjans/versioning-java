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
        rootDirectory.mkdirs();
    }

    @Override
    public Optional<Versions> find(String id) {
        try {
            Versions result = objectMapper.readValue(new File(rootDirectory, id + "versions.json"), Versions.class);
            return Optional.ofNullable(result);
        } catch (IOException e) {
            return Optional.empty();
        }
    }

    @Override
    public void save(String id, Versions version) {
        try {
            objectMapper.writeValue(new File(rootDirectory, id + "versions.json"), version);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
