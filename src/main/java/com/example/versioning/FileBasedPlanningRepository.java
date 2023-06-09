package com.example.versioning;

public class FileBasedPlanningRepository {
    private final VersioningFactory<Planning> hub;

    public FileBasedPlanningRepository(VersioningFactory<Planning> hub) {
        this.hub = hub;
    }

    public void save(Planning planning) {
        hub.buildRepository(planning.id()).createNewVersion(planning);
    }

    public Planning find(String planningId) {
        return hub.buildRepository(planningId).find(planningId);
    }

    public void undo(String planningId) {
        hub.buildRepository(planningId).undo();
    }
}
