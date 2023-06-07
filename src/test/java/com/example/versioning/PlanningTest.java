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
    File tempDir;
    private PlanningRepository repository;

    @BeforeEach
    void setUp() {
        repository = new FileBasedPlanningRepository(tempDir);
    }

    @Test
    void saveEmptyPlanning() {
        repository.save("123", new Planning("my first planning"));
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning"));
    }

    @Test
    void savePlanningWithAnOrder() {
        Planning planning = new Planning("my first planning");
        planning.add(new Order("laptop"));
        repository.save("123", planning);
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning", List.of(new Order("laptop"))));
    }

    @Test
    void undoOnce() {
        Planning planning = new Planning("my first planning");
        repository.save("123", planning);
        planning.add(new Order("laptop"));
        repository.save("123", planning);
        repository.undo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning"));
    }

    @Test
    @Disabled
    void undoTwice() {
        Planning planning = new Planning("my first planning");
        repository.save("123", planning);
        planning.add(new Order("laptop"));
        repository.save("123", planning);
        planning.add(new Order("laptop bag"));
        repository.undo("123");
        repository.undo("123");
        assertThat(repository.find("123")).isEqualTo(new Planning("my first planning"));
    }
}
