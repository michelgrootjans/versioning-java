package com.example.versioning;

public class FileBasedPlanningRepository implements PlanningRepository {
    private final VersionHub<Planning> hub;

    public FileBasedPlanningRepository(VersionHub<Planning> hub) {
        this.hub = hub;
    }

    public void save(Planning planning) {
        hub.buildRepository(planning.id()).createNewVersion(planning);
    }

    public Planning find(String planningId) {
        return hub.buildRepository(planningId).find(planningId);
    }

    @Override
    public void undo(String planningId) {
        hub.buildRepository(planningId).undo();
    }
}
