package com.example.plannings;

import com.example.versioning.VersionRepository;
import com.example.versioning.Versions;

import java.util.UUID;

public class PlanningService {
    private final VersionRepository versions;
    private final PlanningRepository allPlannings;

    public PlanningService(VersionRepository versions, PlanningRepository allPlannings) {
        this.versions = versions;
        this.allPlannings = allPlannings;
    }

    public void save(String planningId, Planning planning) {
        Versions version = this.versions.find(planningId)
            .map(v -> v.push(UUID.randomUUID().toString()))
            .orElse(new Versions(UUID.randomUUID().toString()));

        this.versions.save(planningId, version);
        allPlannings.save(version.head(), planning);
    }

    public Planning find(String planningId) {
        Versions currentVersion = versions.find(planningId).orElseThrow();
        return allPlannings.getPlanning(currentVersion.head());
    }

    public void undo(String planningId) {
        Versions currentVersion = versions.find(planningId).orElseThrow();
        versions.save(planningId, currentVersion.undo());
    }

    public void redo(String planningId) {
        Versions currentVersion = versions.find(planningId).orElseThrow();
        versions.save(planningId, currentVersion.redo());
    }
}
