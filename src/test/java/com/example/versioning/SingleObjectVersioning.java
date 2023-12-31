package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import com.example.plannings.Planning;
import com.example.plannings.PlanningService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;

public class SingleObjectVersioning {
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
    void savePlanning() {
        service.save("123", new Planning("v1"));
        assertThat(service.find("123")).isEqualTo(new Planning("v1"));
    }

    @Test
    void savePlanningTwice() {
        service.save("123", new Planning("v1"));
        service.save("123", new Planning("v2"));
        assertThat(service.find("123")).isEqualTo(new Planning("v2"));
    }

    @Test
    void undo() {
        service.save("123", new Planning("v1"));
        service.save("123", new Planning("v2"));
        service.undo("123");
        assertThat(service.find("123")).isEqualTo(new Planning("v1"));
    }

    @Test
    void undoTwice() {
        service.save("123", new Planning("v1"));
        service.save("123", new Planning("v2"));
        service.save("123", new Planning("v3"));
        service.undo("123");
        service.undo("123");
        assertThat(service.find("123")).isEqualTo(new Planning("v1"));
    }

    @Test
    void redo() {
        service.save("123", new Planning("v1"));
        service.save("123", new Planning("v2"));
        service.undo("123");
        service.redo("123");
        assertThat(service.find("123")).isEqualTo(new Planning("v2"));
    }

    @Test
    void redoTwice() {
        service.save("123", new Planning("v1"));
        service.save("123", new Planning("v2"));
        service.save("123", new Planning("v3"));
        service.undo("123");
        service.undo("123");
        service.redo("123");
        service.redo("123");
        assertThat(service.find("123")).isEqualTo(new Planning("v3"));
    }

    @Test
    void twoPlannings() {
        service.save("123", new Planning("planning 123"));
        service.save("456", new Planning("planning 456"));
        assertThat(service.find("123")).isEqualTo(new Planning("planning 123"));
        assertThat(service.find("456")).isEqualTo(new Planning("planning 456"));
    }
}
