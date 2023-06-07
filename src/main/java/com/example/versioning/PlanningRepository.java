package com.example.versioning;

public interface PlanningRepository {
    void save(Planning planning);

    Planning find(String planningId);

    void undo(String planningId);
}
