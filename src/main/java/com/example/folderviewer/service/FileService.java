package com.example.folderviewer.service;

import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FileService {

    public static class Entry {
        private final String name;
        private final boolean directory;
        private final long size;
        private final String lastModified;
        private final String relativePath;

        public Entry(String name, boolean directory, long size, String lastModified, String relativePath) {
            this.name = name;
            this.directory = directory;
            this.size = size;
            this.lastModified = lastModified;
            this.relativePath = relativePath;
        }

        public String getName() { return name; }
        public boolean isDirectory() { return directory; }
        public long getSize() { return size; }
        public String getLastModified() { return lastModified; }
        public String getRelativePath() { return relativePath; }
    }

    private static final DateTimeFormatter TIME_FMT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")
            .withZone(ZoneId.systemDefault());

    public List<Entry> listDirectory(String inputPath) throws IOException {
        Path path = Paths.get(inputPath).toAbsolutePath().normalize();

        if (!Files.exists(path) || !Files.isDirectory(path)) {
            return List.of();
        }

        try (var stream = Files.list(path)) {
            List<Entry> list = new ArrayList<>();
            for (Path child : stream.collect(Collectors.toList())) {
                BasicFileAttributes attr = Files.readAttributes(child, BasicFileAttributes.class);
                boolean isDir = attr.isDirectory();
                long size = isDir ? 0L : attr.size();
                String lm = TIME_FMT.format(Instant.ofEpochMilli(attr.lastModifiedTime().toMillis()));
                String rel = path.relativize(child).toString();
                list.add(new Entry(child.getFileName().toString(), isDir, size, lm, rel));
            }
            list.sort((a, b) -> {
                if (a.isDirectory() && !b.isDirectory()) return -1;
                if (!a.isDirectory() && b.isDirectory()) return 1;
                return a.getName().compareToIgnoreCase(b.getName());
            });
            return list;
        }
    }
}