package com.example.versioning;

import java.io.File;

public class FileBasedPlanningRepository implements PlanningRepository {
    private final VersionHub hub;

    public FileBasedPlanningRepository(File rootDirectory) {
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
    }
}
