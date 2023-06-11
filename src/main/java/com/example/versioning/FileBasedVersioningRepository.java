package com.example.versioning;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.UUID;

public class FileBasedVersioningRepository<T> implements VersioningRepository<T> {
    private final File rootDirectory;
    private final Class<T> targetType;
    private final ObjectMapper objectMapper = new ObjectMapper();

    public FileBasedVersioningRepository(File rootDirectory, Class<T> targetType) {
        this.rootDirectory = rootDirectory;
        this.targetType = targetType;
    }

    @Override
    public void createNewVersion(T target) {
        String newHash = UUID.randomUUID().toString();
        createNewVersion(newHash, target);
        pointHeadTo(newHash);
        // new impl
        Head2 head = readHead();
        head.pointTo(newHash);
        save(head);
    }

    @Override
    public T currentVersion() {
        return readTarget(head());
    }

    @Override
    public void undo() {
        addToUndoStack(head());
        pointHeadTo(parent());

        // new impl
        Head2 head = readHead();
        head.undo(parent());
        save(head);
    }

    @Override
    public void redo() {
        UndoStack undoStack = currentUndo();
        String hash = undoStack.pop();
        write(new File(rootDirectory, "undo.json"), undoStack);
        pointHeadTo(hash);
    }

    private String head() {
        String newHead = readHead().currentHead();
        return currentHead().hash();
    }

    private void addToUndoStack(String hash) {
        write(new File(rootDirectory, "undo.json"), currentUndo().push(hash));
    }

    private UndoStack currentUndo() {
        File src = new File(rootDirectory, "undo.json");
        if (src.exists()) {
            try {
                return objectMapper.readValue(src, UndoStack.class);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        } else {
            return new UndoStack();
        }
    }

    private T readTarget(String hash) {
        try {
            File versionDirectory = new File(rootDirectory, hash);
            File targetFile = new File(versionDirectory, "target.json");
            return objectMapper.readValue(targetFile, targetType);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void pointHeadTo(String versionHash) {
        write(rootFile("head.json"), new Head(versionHash));
    }

    private Head2 readHead() {
        try {
            List<String> undolist = Files.readAllLines(Path.of(rootDirectory.getAbsolutePath(), "head"), StandardCharsets.UTF_8);
            return new Head2(undolist);
        } catch (IOException e) {
            return new Head2();
        }
    }

    private void save(Head2 head) {
        List<String> undolist = head.undoList();
        String text = String.join("\n", undolist);
        try {
            Files.write(Path.of(rootDirectory.getAbsolutePath(), "head"), text.getBytes());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



    private void createNewVersion(String versionHash, T target) {
        File versionDirectory = directoryOf(versionHash);
        versionDirectory.mkdirs();

        write(new File(versionDirectory, "target.json"), target);
        write(new File(versionDirectory, "message.json"), new Message(head()));
    }

    private File directoryOf(String versionHash) {
        return new File(rootDirectory, versionHash);
    }

    private Head currentHead() {
        try {
            return objectMapper.readValue(rootFile("head.json"), Head.class);
        } catch (IOException e) {
            return new Head("");
        }
    }

    private File rootFile(String fileName) {
        return new File(rootDirectory, fileName);
    }

    private void write(File file, Object data) {
        try {
            objectMapper.writeValue(file, data);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private String parent() {
        File currentVersionDirectory = new File(rootDirectory, head());
        File currentMessageFile = new File(currentVersionDirectory, "message.json");
        Message currentMessage;
        try {
            currentMessage = objectMapper.readValue(currentMessageFile, Message.class);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        String parentHash = currentMessage.parent();
        return parentHash;
    }
}
