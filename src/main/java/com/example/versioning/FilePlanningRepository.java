package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class FilePlanningRepository implements PlanningRepository {
    private final File rootDirectory;

    public FilePlanningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    public void save(Planning planning) {
        File directory = new File(rootDirectory, planning.id());
        if(!directory.exists()) directory.mkdirs();
        File file = new File(directory, "planning.json");
        try {
            new ObjectMapper().writeValue(file, planning);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Planning read(String planningId) {
        File directory = new File(rootDirectory, planningId);
        File file = new File(directory, "planning.json");
        try {
            return new ObjectMapper().readValue(file, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
