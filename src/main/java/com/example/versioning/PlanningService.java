package com.example.versioning;

public class PlanningService {
    private final VersionHub<Planning> hub;
    private final VersionRepository versions;

    public PlanningService(VersionHub<Planning> hub, VersionRepository versions) {
        this.hub = hub;
        this.versions = versions;
    }

    public void save(String id, Planning planning) {
        repoOf(id).createNewVersion(planning);
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
