package com.example.plannings;

import com.example.versioning.VersionRepository;
import com.example.versioning.Versions;

import java.util.List;
import java.util.UUID;

public class PlanningService {
    private final VersionRepository versions;
    private final PlanningRepository latestPlannings;
    private final PlanningRepository allPlannings;

    public PlanningService(VersionRepository versions, PlanningRepository latestPlannings, PlanningRepository allPlannings) {
        this.versions = versions;
        this.latestPlannings = latestPlannings;
        this.allPlannings = allPlannings;
    }

    public Planning find(String planningId) {
        return latestPlannings.getPlanning(planningId);
    }

    public void save(String planningId, Planning planning) {
        String newHead = UUID.randomUUID().toString();
        Versions version = this.versions.find(planningId)
            .map(v -> v.push(newHead))
            .orElse(new Versions(newHead));
        this.versions.save(planningId, version);
        latestPlannings.save(planningId, planning);
        allPlannings.save(version.head(), planning);
    }

    public void undo(String planningId) {
        Versions newVersion = versions.find(planningId).orElseThrow().undo();
        versions.save(planningId, newVersion);
        Planning planning = allPlannings.getPlanning(newVersion.head());
        latestPlannings.save(planningId, planning);
    }

    public void redo(String planningId) {
        Versions newVersion = versions.find(planningId).orElseThrow().redo();
        versions.save(planningId, newVersion);
        Planning planning = allPlannings.getPlanning(newVersion.head());
        latestPlannings.save(planningId, planning);
    }

    public List<Planning> all() {
        return latestPlannings.all();
    }
}
