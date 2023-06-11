package com.example.plannings;

import java.util.List;

public interface PlanningRepository {
    void save(String id, Planning planning);

    Planning getPlanning(String id);

    List<Planning> all();
}
