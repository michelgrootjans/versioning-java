package com.example.versioning;

public interface VersionedRepository {
    <T> void createNewVersion(T t);
}
