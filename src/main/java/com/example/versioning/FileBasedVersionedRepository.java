package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileBasedVersionedRepository implements VersionedRepository {
    private final File rootDirectory;
    private final ObjectMapper objectMapper;

    public FileBasedVersionedRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.objectMapper = new ObjectMapper();
    }

    @Override
    public void createNewVersion(Object target) {
        String versionHash = createNewVersion2(target);
        pointHeadTo(versionHash);
    }

    private void pointHeadTo(String versionHash) {
        write(new File(rootDirectory, "head.json"), new Head(versionHash));
    }

    private String createNewVersion2(Object planning) {
        String versionHash = UUID.randomUUID().toString();
        File versionDirectory = new File(rootDirectory, versionHash);
        versionDirectory.mkdirs();

        write(new File(versionDirectory, "planning.json"), planning);
        write(new File(versionDirectory, "message.json"), new Message(currentHead(rootDirectory).hash()));
        return versionHash;
    }


    private Head currentHead(File directory) {
        try {
            return objectMapper.readValue(new File(directory, "head.json"), Head.class);
        } catch (IOException e) {
            return new Head("");
        }
    }

    private void write(File file, Object data) {
        try {
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
