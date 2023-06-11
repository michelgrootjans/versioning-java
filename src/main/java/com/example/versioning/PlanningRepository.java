package com.example.versioning;

public interface PlanningRepository {
    void save(String id, Planning planning);

    Planning find(String id);
}
