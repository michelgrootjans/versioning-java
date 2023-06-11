package com.example.versioning;

import com.example.plannings.Planning;
import com.example.plannings.PlanningRepository;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

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
            objectMapper.writeValue(planningFile(id), planning);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Planning getPlanning(String id) {
        return getPlanning(planningFile(id));
    }

    @Override
    public List<Planning> all() {
        return Arrays.stream(rootDirectory.listFiles())
            .map(this::getPlanning)
            .toList();
    }

    private Planning getPlanning(File targetFile) {
        try {
            return objectMapper.readValue(targetFile, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private File planningFile(String id) {
        return new File(rootDirectory, "%s.json".formatted(id));
    }
}
