package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

public class FileBasedPlanningRepository implements PlanningRepository {
    private final File rootDirectory;
    private final ObjectMapper objectMapper;
    private final VersionHub hub;

    public FileBasedPlanningRepository(File rootDirectory) {
        this.rootDirectory = rootDirectory;
        this.objectMapper = new ObjectMapper();
        this.hub = new FileBasedVersionHub(rootDirectory);
    }

    public void save(String id, Planning planning) {
        File planningDirectory = new File(rootDirectory, id);
        String versionHash = createNewVersion(planning, planningDirectory);
        pointHeadTo(planningDirectory, versionHash);
    }

    private void pointHeadTo(File planningDirectory, String versionHash) {
        write(new File(planningDirectory, "head.json"), new Head(versionHash));
    }

    private String createNewVersion(Planning planning, File planningDirectory) {
        String versionHash = UUID.randomUUID().toString();
        File versionDirectory = new File(planningDirectory, versionHash);
        versionDirectory.mkdirs();

        write(new File(versionDirectory, "planning.json"), planning);
        write(new File(versionDirectory, "message.json"), new Message(currentHead(planningDirectory).hash()));
        return versionHash;
    }

    private void write(File file, Object data) {
        try {
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public Planning find(String planningId) {
        File directory = new File(rootDirectory, planningId);
        try {
            Head head = currentHead(directory);
            File versionDirectory = new File(directory, head.hash());
            File planningFile = new File(versionDirectory, "planning.json");
            return objectMapper.readValue(planningFile, Planning.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private Head currentHead(File directory) {
        try {
            return objectMapper.readValue(new File(directory, "head.json"), Head.class);
        } catch (IOException e) {
            return new Head("");
        }
    }

    @Override
    public void undo(String planningId) {
        File planningDirectory = new File(rootDirectory, planningId);
        Head head = currentHead(planningDirectory);
        File currentVersionDirectory = new File(planningDirectory, head.hash());
        File currentMessageFile = new File(currentVersionDirectory, "message.json");
            Message currentMessage;
        try {
            currentMessage = objectMapper.readValue(currentMessageFile, Message.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        pointHeadTo(planningDirectory, currentMessage.parent());
    }
}
