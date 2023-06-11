package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.List;

public class PlanningTest {
    @TempDir
    private File tempDir;

    private PlanningService repository;

    @BeforeEach
    void setUp() {
        repository = new PlanningService(
            new FileBasedVersionHub<>(tempDir, Planning.class),
            new FileBasedVersionRepository(tempDir)
        );
    }

    @Test
    void saveEmptyPlanning() {
        repository.save("123", new Planning("my first planning"));
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning"));
    }

    @Test
    void savePlanningWithAnOrder() {
        repository.save("123", new Planning("my first planning", List.of(new Order("laptop"))));
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning", List.of(new Order("laptop"))));
    }

    @Test
    void undo() {
        repository.save("123", new Planning("my first planning"));
        repository.save("123", new Planning("my first planning", List.of(new Order("laptop"))));
        repository.undo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning"));
    }

    @Test
    void undoTwice() {
        repository.save("123", new Planning("my first planning"));
        repository.save("123", new Planning("my first planning", List.of(new Order("laptop"))));
        repository.save("123", new Planning("my first planning", List.of(new Order("laptop bag"))));
        repository.undo("123");
        repository.undo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning"));
    }

    @Test
    void redo() {
        repository.save("123", new Planning("my first planning"));
        repository.save("123", new Planning("my first planning", List.of(new Order("laptop"))));
        repository.undo("123");
        repository.redo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning", List.of(new Order("laptop"))));
    }

    @Test
    void redoTwice() {
        repository.save("123", new Planning("planning 1"));
        repository.save("123", new Planning("planning 2"));
        repository.save("123", new Planning("planning 3"));
        repository.undo("123");
        repository.undo("123");
        repository.redo("123");
        repository.redo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("planning 3"));
    }

    @Test
    @Disabled
    void twoPlannings() {
        repository.save("123", new Planning("planning 1"));
        repository.save("456", new Planning("planning 2"));
        assertThat(repository.find("123")).isEqualTo(new Planning("planning 1"));
        assertThat(repository.find("456")).isEqualTo(new Planning("planning 2"));
    }
}
