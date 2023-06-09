package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

public class FileBasedVersioningRepository<T> implements VersioningRepository<T> {
    private final File rootDirectory;
    private final Class<T> targetType;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileBasedVersioningRepository(File rootDirectory, Class<T> targetType) {
        this.rootDirectory = rootDirectory;
        this.targetType = targetType;
    }

    @Override
    public void createNewVersion(T target) {
        String versionHash = UUID.randomUUID().toString();
        createNewVersion(target, versionHash);
        pointHeadTo(versionHash);
    }

    @Override
    public T currentVersion() {
        try {
            Head head = currentHead();
            File versionDirectory = new File(rootDirectory, head.hash());
            File targetFile = new File(versionDirectory, "target.json");
            return objectMapper.readValue(targetFile, targetType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void undo() {
        pointHeadTo(parentHash());
    }

    @Override
    public void redo() {
            Planning planning = new Planning("123", "my first planning", List.of(new Order("laptop")));
            createNewVersion((T) planning);
    }

    private void pointHeadTo(String versionHash) {
        write(rootFile("head.json"), new Head(versionHash));
    }

    private void createNewVersion(T target, String versionHash) {
        File versionDirectory = directoryOf(versionHash);
        versionDirectory.mkdirs();

        write(new File(versionDirectory, "target.json"), target);
        write(new File(versionDirectory, "message.json"), new Message(currentHead().hash()));
    }

    private File directoryOf(String versionHash) {
        return new File(rootDirectory, versionHash);
    }

    private Head currentHead() {
        try {
            return objectMapper.readValue(rootFile("head.json"), Head.class);
        } catch (IOException e) {
            return new Head("");
        }
    }

    private File rootFile(String fileName) {
        return new File(rootDirectory, fileName);
    }

    private void write(File file, Object data) {
        try {
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String parentHash() {
        File currentVersionDirectory = new File(rootDirectory, currentHead().hash());
        File currentMessageFile = new File(currentVersionDirectory, "message.json");
        Message currentMessage;
        try {
            currentMessage = objectMapper.readValue(currentMessageFile, Message.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String parentHash = currentMessage.parent();
        return parentHash;
    }
}
