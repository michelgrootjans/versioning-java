package com.example.versioning;

import java.util.List;
import java.util.stream.Stream;

public class ListAppender {
    static List<Version> append(Version version, List<Version> versions) {
        return Stream.concat(
            versions.stream(),
            Stream.of(version)
        ).toList();
    }
}
