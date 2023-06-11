package com.example.versioning;

import java.util.List;
import java.util.stream.Stream;

public class ListAppender {
    static <T> List<T> append(T version, List<T> versions) {
        return Stream.concat(
            versions.stream(),
            Stream.of(version)
        ).toList();
    }
}
