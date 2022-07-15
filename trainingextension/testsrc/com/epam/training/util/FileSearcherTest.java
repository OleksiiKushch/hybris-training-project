package com.epam.training.util;

import junit.framework.TestCase;

import java.io.File;
import java.util.List;

public class FileSearcherTest extends TestCase {

    public void testGetAllFiles() {
        List<File> result1 = FileSearcher.getAllFiles(
                new File("resources/test/customImportService"), "impex");
        List<File> result2 = FileSearcher.getAllFiles(
                new File("resources/test/customImportService"), "csv");

        assertEquals(3, result1.size());
        assertEquals(0, result2.size());
    }
}