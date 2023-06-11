package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

class Head2Test {
    @Test
    void noVersions() {
        assertThat(new Head2().currentHead()).isEmpty();
    }
}