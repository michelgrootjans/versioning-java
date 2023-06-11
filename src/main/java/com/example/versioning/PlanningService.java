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

    public Planning find(String planningId) {
        Versions version = versions.find(planningId).orElseThrow();
        return plannings.getPlanning(version.head());
    }

    public void save(String planningId, Planning planning) {
        repoOf(planningId).createNewVersion(planning);
    }

    public void undo(String planningId) {
        Versions version = versions.find(planningId).orElseThrow();
        versions.save(planningId, version.undo());
    }

    public void redo(String planningId) {
        Versions version = versions.find(planningId).orElseThrow();
        versions.save(planningId, version.redo());
    }

    private VersioningRepository<Planning> repoOf(String planningId) {
        return hub.buildRepository("planning-" + planningId);
    }
}
