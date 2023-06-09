package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileBasedVersioningRepository<T> implements VersioningRepository<T> {
    private final File rootDirectory;
    private final ObjectMapper objectMapper;
    private final Class<Planning> targetType;

    public FileBasedVersioningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.objectMapper = new ObjectMapper();
        targetType = Planning.class;
    }

    @Override
    public void createNewVersion(T target) {
        String versionHash = createNewVersion2(target);
        pointHeadTo(versionHash);
    }

    @Override
    public Planning currentVersion() {
        try {
            Head head = currentHead();
            File versionDirectory = new File(rootDirectory, head.hash());
            File planningFile = new File(versionDirectory, targetName() + ".json");
            return objectMapper.readValue(planningFile, targetType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String targetName() {
        return targetType.getSimpleName();
    }

    @Override
    public void undo() {
        Head head = currentHead();
        File currentVersionDirectory = new File(rootDirectory, head.hash());
        File currentMessageFile = new File(currentVersionDirectory, "message.json");
        Message currentMessage;
        try {
            currentMessage = objectMapper.readValue(currentMessageFile, Message.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pointHeadTo(currentMessage.parent());
    }

    private void pointHeadTo(String versionHash) {
        write(new File(rootDirectory, "head.json"), new Head(versionHash));
    }

    private String createNewVersion2(T target) {
        String versionHash = UUID.randomUUID().toString();
        File versionDirectory = new File(rootDirectory, versionHash);
        versionDirectory.mkdirs();

        write(new File(versionDirectory, targetName() + ".json"), target);
        write(new File(versionDirectory, "message.json"), new Message(currentHead().hash()));
        return versionHash;
    }

    private Head currentHead() {
        try {
            return objectMapper.readValue(new File(rootDirectory, "head.json"), Head.class);
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
