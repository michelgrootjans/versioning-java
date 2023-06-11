package com.example.versioning;

public class PlanningService {
    private final VersionHub<Planning> hub;
    private final VersionRepository versions;
    private final PlanningRepository plannings;

    public PlanningService(VersionHub<Planning> hub, VersionRepository versions, PlanningRepository plannings) {
        this.hub = hub;
        this.versions = versions;
        this.plannings = plannings;
    }

    public void save(String planningId, Planning planning) {
        versions.find(planningId)
            .map(Versions::head)
            .ifPresent(hash -> plannings.save(hash, planning));
        repoOf(planningId).createNewVersion(planning);
    }

    public Planning find(String planningId) {
        var currentVersion = versions.headOf(planningId);
        return plannings.find(currentVersion);
    }

    public void undo(String planningId) {
        versions.undo(planningId);
        repoOf(planningId).undo();
    }

    public void redo(String planningId) {
        versions.redo(planningId);
        repoOf(planningId).redo();
    }

    private VersioningRepository<Planning> repoOf(String planningId) {
        return hub.buildRepository("planning-" + planningId);
    }
}
