package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.plannings.Order;
import com.example.plannings.Planning;
import com.example.plannings.PlanningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.util.List;

public class PlanningTest {
    @TempDir
    private File tempDir;

    private PlanningService service;

    @BeforeEach
    void setUp() {
        service = new PlanningService(
            new FileBasedVersionRepository(new File(tempDir, "versions")),
            new FileBasedPlanningRepository(new File(tempDir, "plannings"))
        );
    }

    @Test
    void saveEmptyPlanning() {
        service.save("123", new Planning("my first planning"));
        assertThat(service.find("123")).isEqualTo(new Planning("my first planning"));
    }

    @Test
    void savePlanningWithAnOrder() {
        service.save("123", new Planning("my first planning", List.of(new Order("laptop"))));
        assertThat(service.find("123")).isEqualTo(new Planning("my first planning", List.of(new Order("laptop"))));
    }

    @Test
    void undo() {
        service.save("123", new Planning("my first planning"));
        service.save("123", new Planning("my first planning", List.of(new Order("laptop"))));
        service.undo("123");
        assertThat(service.find("123")).isEqualTo(new Planning("my first planning"));
    }

    @Test
    void undoTwice() {
        service.save("123", new Planning("my first planning"));
        service.save("123", new Planning("my first planning", List.of(new Order("laptop"))));
        service.save("123", new Planning("my first planning", List.of(new Order("laptop bag"))));
        service.undo("123");
        service.undo("123");
        assertThat(service.find("123")).isEqualTo(new Planning("my first planning"));
    }

    @Test
    void redo() {
        service.save("123", new Planning("my first planning"));
        service.save("123", new Planning("my first planning", List.of(new Order("laptop"))));
        service.undo("123");
        service.redo("123");
        assertThat(service.find("123")).isEqualTo(new Planning("my first planning", List.of(new Order("laptop"))));
    }

    @Test
    void redoTwice() {
        service.save("123", new Planning("planning 1"));
        service.save("123", new Planning("planning 2"));
        service.save("123", new Planning("planning 3"));
        service.undo("123");
        service.undo("123");
        service.redo("123");
        service.redo("123");
        assertThat(service.find("123")).isEqualTo(new Planning("planning 3"));
    }

    @Test
    void twoPlannings() {
        service.save("123", new Planning("planning 1"));
        service.save("456", new Planning("planning 2"));
        assertThat(service.find("123")).isEqualTo(new Planning("planning 1"));
        assertThat(service.find("456")).isEqualTo(new Planning("planning 2"));
    }
}
