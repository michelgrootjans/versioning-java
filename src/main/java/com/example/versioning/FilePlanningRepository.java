package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FilePlanningRepository implements PlanningRepository {
    private final File rootDirectory;

    public FilePlanningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public Planning read() {
        File file = new File(rootDirectory, "car.json");
        try {
            return new ObjectMapper().readValue(file, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void save(Planning planning) {
        File file = new File(rootDirectory, "car.json");
        try {
            new ObjectMapper().writeValue(file, planning);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
