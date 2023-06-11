package com.example.versioning;

import java.util.UUID;

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
        String newHash = UUID.randomUUID().toString();
        Versions version = versions.find(planningId)
            .map(v -> v.push(newHash))
            .orElse(new Versions(newHash));
        versions.save(planningId, version);
        plannings.save(version.head(), planning);
//        hub.buildRepository("planning-" + planningId).createNewVersion(planning, version.head());
    }

    public void undo(String planningId) {
        Versions version = versions.find(planningId).orElseThrow();
        versions.save(planningId, version.undo());
    }

    public void redo(String planningId) {
        Versions version = versions.find(planningId).orElseThrow();
        versions.save(planningId, version.redo());
    }
}
