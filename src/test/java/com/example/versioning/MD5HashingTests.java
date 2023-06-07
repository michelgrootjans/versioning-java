package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.NoSuchAlgorithmException;

public class MD5HashingTests {

    private Hasher md5Hasher;

    @BeforeEach
    void setUp() {
        md5Hasher = new MD5Hasher();
    }

    @Test
    void someMD5hashing() throws NoSuchAlgorithmException {
        assertThat(md5Hasher.getHash("ILoveJava")).isEqualTo("35454B055CC325EA1AF2126E27707052");
    }
}
