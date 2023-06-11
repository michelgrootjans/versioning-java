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

    public Planning find(String planningId) {
        Versions version = versions.find(planningId).orElseThrow();
        return plannings.getPlanning(planningId);
    }

    public void save(String planningId, Planning planning) {
        String newHead = UUID.randomUUID().toString();
        Versions version = this.versions.find(planningId)
            .map(v -> v.push(newHead))
            .orElse(new Versions(newHead));
        this.versions.save(planningId, version);
        plannings.save(planningId, planning);
        plannings.save(version.head(), planning);
    }

    public void undo(String planningId) {
        Versions newVersion = versions.find(planningId).orElseThrow().undo();
        versions.save(planningId, newVersion);
        Planning planning = plannings.getPlanning(newVersion.head());
        plannings.save(planningId, planning);
    }

    public void redo(String planningId) {
        Versions newVersion = versions.find(planningId).orElseThrow().redo();
        versions.save(planningId, newVersion);
        Planning planning = plannings.getPlanning(newVersion.head());
        plannings.save(planningId, planning);
    }
}
