package com.example.versioning;

import java.util.ArrayList;
import java.util.List;

public class Head2 {
    private final List<String> undolist;

    public Head2() {
        this(new ArrayList<>());
    }

    public Head2(List<String> undolist) {
        this.undolist = undolist;
    }

    public String currentHead() {
        if (undolist.isEmpty()) return "";
        return undolist.get(0);
    }

    public void pointTo(String newHash) {
        undolist.clear();
        undolist.add(newHash);
    }

    public List<String> undoList() {
        return undolist;
    }

    public void undo(String parent) {

    }
}
