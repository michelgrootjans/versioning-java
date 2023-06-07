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
    void saveToWorkspace() {
        Planning planning = new Planning("123");
        planning.add(new Order("laptop"));

        repository.save(planning);
        assertThat(repository.read("123")).isEqualTo(new Planning("123", List.of(new Order("laptop"))));
    }
}
