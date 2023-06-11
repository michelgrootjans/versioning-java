package com.example.versioning;

import com.example.plannings.Planning;
import com.example.plannings.PlanningRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FileBasedPlanningRepository implements PlanningRepository {
    private final File rootDirectory;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileBasedPlanningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        rootDirectory.mkdirs();
    }

    @Override
    public void save(String id, Planning planning) {
        try {
            objectMapper.writeValue(new File(rootDirectory, "%s.json".formatted(id)), planning);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }

    @Override
    public Planning getPlanning(String id) {
        try {
            File targetFile = new File(rootDirectory, "%s.json".formatted(id));
            return objectMapper.readValue(targetFile, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
