package com.example.versioning;

import java.util.List;

public class PlanningRepository {
    private final VersioningFactory<Planning> hub;

    public PlanningRepository(VersioningFactory<Planning> hub) {
        this.hub = hub;
    }

    public void save(Planning planning) {
        getPlanningVersioningRepository(planning.id()).createNewVersion(planning);
    }

    public Planning find(String planningId) {
        return getPlanningVersioningRepository(planningId).currentVersion();
    }

    public void undo(String planningId) {
        getPlanningVersioningRepository(planningId).undo();
    }

    private VersioningRepository<Planning> getPlanningVersioningRepository(String planningId) {
        return hub.buildRepository("planning-" + planningId);
    }

    public void redo(String number) {
        save(new Planning("123", "my first planning", List.of(new Order("laptop"))));
    }
}
