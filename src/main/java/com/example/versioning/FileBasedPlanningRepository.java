package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileBasedPlanningRepository implements PlanningRepository {
    private final File rootDirectory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileBasedPlanningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public void save(String id, Planning planning) {

    }

    @Override
    public Planning find(String id) {
        try {
            File targetFile = new File(rootDirectory, "%s.json".formatted(id));
            return objectMapper.readValue(targetFile, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
