package com.epam.training.util;

import org.apache.commons.io.FilenameUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class FileSearcher {
    private FileSearcher() {
    }

    public static List<File> getAllFiles(File rootDirectory, String extension) {
        List<File> result = new ArrayList<>();
        if (rootDirectory.isDirectory()) {
            recursiveFileCollector(rootDirectory.listFiles(), extension, result);
        }
        return result;
    }

    private static void recursiveFileCollector(File[] fileArray, String extension, List<File> result) {
        for (File file : fileArray) {
            if (file.isFile() && FilenameUtils.getExtension(file.getName()).equals(extension)) {
                result.add(file);
            } else if (file.isDirectory()) {
                recursiveFileCollector(Objects.requireNonNull(file.listFiles()), extension, result);
            }
        }
    }
}
