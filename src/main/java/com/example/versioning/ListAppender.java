package com.example.versioning;

import java.util.List;
import java.util.stream.Stream;

public class ListAppender {
    private ListAppender() {
    }

    static <T> List<T> append(T item, List<T> list) {
        return Stream.concat(
            list.stream(),
            Stream.of(item)
        ).toList();
    }
}
