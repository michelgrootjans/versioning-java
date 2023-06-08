package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

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
        hub.buildRepository(id).createNewVersion(planning);
    }

    public Planning find(String planningId) {
        return hub.buildRepository(planningId).find(planningId);
    }

    @Override
    public void undo(String planningId) {
        hub.buildRepository(planningId).undo();
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

    private void pointHeadTo(File planningDirectory, String versionHash) {
        write(new File(planningDirectory, "head.json"), new Head(versionHash));
    }

    private void write(File file, Object data) {
        try {
            objectMapper.writeValue(file, data);
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
}
