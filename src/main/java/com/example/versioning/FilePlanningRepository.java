package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FilePlanningRepository implements PlanningRepository {
    private final File rootDirectory;

    public FilePlanningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public void save(Planning planning) {
        File directory = new File(rootDirectory, "123");
        if(!directory.exists()) directory.mkdirs();
        File file = new File(directory, "car.json");
        try {
            new ObjectMapper().writeValue(file, planning);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Planning read(String planningId) {
        File directory = new File(rootDirectory, "123");
        File file = new File(directory, "car.json");
        try {
            return new ObjectMapper().readValue(file, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
