package com.example.versioning;

import java.util.Optional;

public interface VersionRepository {
    Optional<Versions> find(String id);

    void save(String planningId, Versions version);
}
