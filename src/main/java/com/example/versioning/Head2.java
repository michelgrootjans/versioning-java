package com.example.versioning;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Head2 {
    private final List<String> undolist;
    private final Stack<String> undoStack;

    public Head2() {
        this(new ArrayList<>());
    }

    public Head2(List<String> undolist) {
        this.undolist = undolist;
        this.undoStack = new Stack<>();
        for (String s : undolist) {
            undoStack.push(s);
        }
    }

    public String currentHead() {
        if (undolist.isEmpty()) return "";
        return undolist.get(0);
    }

    public void pointTo(String newHash) {
        undolist.clear();
        undolist.add(newHash);

        undoStack.clear();
        undoStack.push(newHash);
    }

    public List<String> undoList() {
        return undolist;
    }

    public void undo(String parent) {
        undolist.add(parent);
        undoStack.push(parent);
    }

    public void redo() {
        undolist.remove(currentHead());
        undoStack.pop();
    }
}
