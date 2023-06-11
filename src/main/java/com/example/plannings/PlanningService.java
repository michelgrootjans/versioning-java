package com.example.plannings;

import com.example.versioning.VersionRepository;
import com.example.versioning.Versions;

import java.util.UUID;

public class PlanningService {
    private final VersionRepository versions;
    private final PlanningRepository plannings;

    public PlanningService(VersionRepository versions, PlanningRepository plannings) {
        this.versions = versions;
        this.plannings = plannings;
    }

    public void save(String planningId, Planning planning) {
        String newHash = UUID.randomUUID().toString();
        Versions version = this.versions.find(planningId)
            .map(v -> v.push(newHash))
            .orElse(new Versions(newHash));
        this.versions.save(planningId, version);
        plannings.save(version.head(), planning);
    }

    public Planning find(String planningId) {
        Versions version = versions.find(planningId).orElseThrow();
        return plannings.getPlanning(version.head());
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