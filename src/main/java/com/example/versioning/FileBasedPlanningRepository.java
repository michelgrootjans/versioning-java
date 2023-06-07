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
        String versionHash = UUID.randomUUID().toString();
        File planningDirectory = new File(rootDirectory, id);
        File versionDirectory = new File(planningDirectory, versionHash);
        versionDirectory.mkdirs();

        write(new File(versionDirectory, "planning.json"), planning);
        write(new File(versionDirectory, "message.json"), new Message(currentHead(planningDirectory).hash()));
        write(new File(planningDirectory, "head.json"), new Head(versionHash, currentHead(planningDirectory).hash()));
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
            Head head = currentHead(directory);
            File versionDirectory = new File(directory, head.hash());
            File planningFile = new File(versionDirectory, "planning.json");
            return objectMapper.readValue(planningFile, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Head currentHead(File directory) {
        try {
            return objectMapper.readValue(new File(directory, "head.json"), Head.class);
        } catch (IOException e) {
            return new Head("", "");
        }
    }

    @Override
    public void undo(String planningId) {
        File directory = new File(rootDirectory, planningId);
        Head head = currentHead(directory);
        write(new File(directory, "head.json"), head.undo());
    }
}
