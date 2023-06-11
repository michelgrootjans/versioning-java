package com.example.versioning;

public interface PlanningRepository {
    void save(String id, Planning planning);

    Planning getPlanning(String id);
}
