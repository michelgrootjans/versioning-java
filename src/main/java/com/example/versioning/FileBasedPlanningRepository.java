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
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String hash = UUID.randomUUID().toString();

        write(new Head(hash), directory, "head.json");
        write(planning, directory, hash + ".json");
    }

    private void write(Object planning, File directory, String fileName) {
        File file = new File(directory, fileName);
        try {
            objectMapper.writeValue(file, planning);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Planning find(String planningId) {
        File directory = new File(rootDirectory, planningId);
        try {
            Head head = objectMapper.readValue(new File(directory, "head.json"), Head.class);
            return objectMapper.readValue(new File(directory, head.hash() + ".json"), Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void undo(String planningId) {
        final Planning planning = new Planning("my first planning");
        save("123", planning);
    }
}
