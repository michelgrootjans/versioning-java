package com.example.plannings;

public interface PlanningRepository {
    void save(String id, Planning planning);

    Planning getPlanning(String id);
}
