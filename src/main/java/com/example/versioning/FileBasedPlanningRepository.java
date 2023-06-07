package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;

import jakarta.xml.bind.DatatypeConverter;

public class FileBasedPlanningRepository implements PlanningRepository {
    private final File rootDirectory;
    private ObjectMapper objectMapper;

    public FileBasedPlanningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        objectMapper = new ObjectMapper();
    }

    public void save(Planning planning) {
        File directory = new File(rootDirectory, planning.id());
        if (!directory.exists()) {
            directory.mkdirs();
        }

        String hash = new MD5Hasher().getHash("blah");

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
            File headFile = new File(directory, "head.json");
            Head head = objectMapper.readValue(headFile, Head.class);
            File planningFile = new File(directory, head.hash() + ".json");
            return objectMapper.readValue(planningFile, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void undo(String planningId) {
        save(new Planning("123"));
    }
}
