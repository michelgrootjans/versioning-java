package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileBasedPlanningRepository implements PlanningRepository {
    private final File rootDirectory;
    private final ObjectMapper objectMapper;

    public FileBasedPlanningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.objectMapper = new ObjectMapper();
    }

    public void save(String id, Planning planning) {
        File directory = new File(rootDirectory, id);
        String versionHash = UUID.randomUUID().toString();
        File versionDirectory = new File(directory, versionHash);
        directory.mkdirs();
        Head parent = getHead(directory);

        write(new File(directory, versionHash + ".json"), planning);
        write(new File(directory, "head.json"), new Head(versionHash, parent.hash()));
    }

    private void write(File file, Object data) {
        try {
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Planning find(String planningId) {
        File directory = new File(rootDirectory, planningId);
        try {
            Head head = getHead(directory);
            return objectMapper.readValue(new File(directory, head.hash() + ".json"), Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Head getHead(File directory) {
        try {
            return objectMapper.readValue(new File(directory, "head.json"), Head.class);
        } catch (IOException e) {
            return new Head("", "");
        }
    }

    @Override
    public void undo(String planningId) {
        File directory = new File(rootDirectory, planningId);
        Head head = getHead(directory);
        write(new File(directory, "head.json"), head.undo());
    }
}
