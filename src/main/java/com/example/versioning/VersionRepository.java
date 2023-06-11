package com.example.versioning;

import java.util.Optional;

public interface VersionRepository {
    Optional<Versions> find(String id);

    String headOf(String id);

    void undo(String id);

    void redo(String id);

}
