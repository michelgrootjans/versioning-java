package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Head2Test {
    @Test
    void noVersions() {
        assertThat(new Head2().currentHead()).isEmpty();
    }

    @Test
    void oneVersion() {
        Head2 head = new Head2();
        head.pointTo("123");
        assertThat(head.currentHead()).isEqualTo("123");
    }

    @Test
    void twoVersions() {
        Head2 head = new Head2();
        head.pointTo("123");
        head.pointTo("456");
        assertThat(head.currentHead()).isEqualTo("456");
    }
}