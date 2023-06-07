package com.example.versioning;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.xml.bind.DatatypeConverter;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.File;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5HashingTests {
    @Test
    void someMD5hashing() throws NoSuchAlgorithmException {
        String password = "ILoveJava";
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());
        byte[] digest = md.digest();
        assertThat(DatatypeConverter.printHexBinary(digest)).isEqualTo("35454B055CC325EA1AF2126E27707052");
    }
}
