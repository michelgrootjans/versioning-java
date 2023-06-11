package com.example.plannings;

import com.example.versioning.VersionRepository;
import com.example.versioning.Versions;

import java.util.UUID;

public class PlanningService {
    private final VersionRepository versions;
    private final PlanningRepository latestVersions;
    private final PlanningRepository allVersions;

    public PlanningService(VersionRepository versions, PlanningRepository plannings) {
        this.versions = versions;
        this.latestVersions = plannings;
        this.allVersions = plannings;
    }

    public Planning find(String planningId) {
        return latestVersions.getPlanning(planningId);
    }

    public void save(String planningId, Planning planning) {
        String newHead = UUID.randomUUID().toString();
        Versions version = this.versions.find(planningId)
            .map(v -> v.push(newHead))
            .orElse(new Versions(newHead));
        this.versions.save(planningId, version);
        latestVersions.save(planningId, planning);
        allVersions.save(version.head(), planning);
    }

    public void undo(String planningId) {
        Versions newVersion = versions.find(planningId).orElseThrow().undo();
        versions.save(planningId, newVersion);
        Planning planning = allVersions.getPlanning(newVersion.head());
        latestVersions.save(planningId, planning);
    }

    public void redo(String planningId) {
        Versions newVersion = versions.find(planningId).orElseThrow().redo();
        versions.save(planningId, newVersion);
        Planning planning = allVersions.getPlanning(newVersion.head());
        latestVersions.save(planningId, planning);
    }
}
