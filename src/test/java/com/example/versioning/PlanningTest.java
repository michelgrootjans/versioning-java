package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.List;

public class PlanningTest {
    @TempDir
    private File tempDir;

    private FileBasedPlanningRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FileBasedPlanningRepository(new FileBasedVersionHub<Planning>(tempDir));
    }

    @Test
    void saveEmptyPlanning() {
        repository.save(new Planning("123", "my first planning"));
        assertThat(repository.find("123")).isEqualTo(new Planning("123", "my first planning"));
    }

    @Test
    void savePlanningWithAnOrder() {
        repository.save(new Planning("123", "my first planning", List.of(new Order("laptop"))));
        assertThat(repository.find("123")).isEqualTo(new Planning("123", "my first planning", List.of(new Order("laptop"))));
    }

    @Test
    void undoOnce() {
        repository.save(new Planning("123", "my first planning"));
        repository.save(new Planning("123", "my first planning", List.of(new Order("laptop"))));
        repository.undo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("123", "my first planning"));
    }

    @Test
    void undoTwice() {
        repository.save(new Planning("123", "my first planning"));
        repository.save(new Planning("123", "my first planning", List.of(new Order("laptop"))));
        repository.save(new Planning("123", "my first planning", List.of(new Order("laptop bag"))));
        repository.undo("123");
        repository.undo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("123", "my first planning"));
    }
}
