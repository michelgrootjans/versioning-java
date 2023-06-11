package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

public class FileBasedVersioningRepository<T> implements VersioningRepository<T> {
    private final File rootDirectory;
    private final Class<T> targetType;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileBasedVersioningRepository(File rootDirectory, Class<T> targetType) {
        this.rootDirectory = rootDirectory;
        this.targetType = targetType;
    }

    @Override
    public void createNewVersion(T target) {
        String newHash = UUID.randomUUID().toString();
        createNewVersion(newHash, target);

        Versions add = getVersions()
            .map(v -> v.push(newHash))
            .orElse(new Versions(newHash));
        writeFile(new File(rootDirectory, "versions.json"), add);
    }

    @Override
    public T currentVersion() {
        String head = getVersions().orElseThrow().head();
        return readTarget(head);
    }

    @Override
    public void undo() {
        Versions add = getVersions()
            .map(Versions::undo)
            .orElseThrow();
        writeFile(new File(rootDirectory, "versions.json"), add);
    }

    @Override
    public void redo() {
        Versions add = getVersions()
            .map(Versions::redo)
            .orElseThrow();
        writeFile(new File(rootDirectory, "versions.json"), add);
    }

    private T readTarget(String hash) {
        try {
            File targetFile = new File(rootDirectory, hash + ".json");
            return objectMapper.readValue(targetFile, targetType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void createNewVersion(String versionHash, T target) {
        writeFile(new File(rootDirectory, versionHash + ".json"), target);
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

    private void writeFile(File file, Object data) {
        try {
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
