package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

public class PlanningTest {
    @TempDir
    File tempDir;
    private PlanningRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FilePlanningRepository(tempDir);
    }

    @Test
    void saveToWorkspace() {
        Planning planning = new Planning("123");

        repository.save(planning);
        assertThat(repository.read("123")).isEqualTo(new Planning("123"));
    }
}
