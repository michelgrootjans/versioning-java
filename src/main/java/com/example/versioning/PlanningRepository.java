package com.example.versioning;

public class PlanningRepository {
    private final VersioningFactory<Planning> hub;

    public PlanningRepository(VersioningFactory<Planning> hub) {
        this.hub = hub;
    }

    public void save(Planning planning) {
        repoOf(planning.id()).createNewVersion(planning);
    }

    public Planning find(String planningId) {
        return repoOf(planningId).currentVersion();
    }

    public void undo(String planningId) {
        repoOf(planningId).undo();
    }

    public void redo(String planningId) {
        repoOf(planningId).redo();
    }

    private VersioningRepository<Planning> repoOf(String planningId) {
        return hub.buildRepository("planning-" + planningId);
    }
}
