package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.List;

public class PlanningTest {
    @TempDir
    File tempDir;
    private PlanningRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FileBasedPlanningRepository(tempDir);
    }

    @Test
    void saveEmptyPlanning() {
        repository.save(new Planning("123"));
        assertThat(repository.find("123")).isEqualTo(new Planning("123"));
    }

    @Test
    void savePlanningWithAnOrder() {
        repository.save(new Planning("123", List.of(new Order("laptop"))));
        assertThat(repository.find("123")).isEqualTo(new Planning("123", List.of(new Order("laptop"))));
    }

    @Test
    void undoPlanningWithAnOrder() {
        Planning planning = new Planning("123");
        repository.save(planning);
        planning.add(new Order("laptop"));
        repository.save(planning);
        repository.undo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("123"));
    }
}
