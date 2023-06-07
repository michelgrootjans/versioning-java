package com.example.versioning;

public interface PlanningRepository {
    void save(Planning planning);
    Planning read();
}
