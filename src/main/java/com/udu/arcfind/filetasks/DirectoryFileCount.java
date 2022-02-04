package com.udu.arcfind.filetasks;

import java.io.File;

public class DirectoryFileCount {

    private static int getIt(File directory) {
        int count = 0;
        File[] files = directory.listFiles();

        if (files != null)
            for (File value : files) {
                //don't count dir
                if (!value.isDirectory()) {
                    count++;
                }

                if (value.isDirectory()) {
                    getIt(value.getAbsoluteFile());
                }
            }
        return count;
    }

    private static int getExtension(File directory, String extension) {
        File[] files = directory.listFiles();
        int count = 0;

        if (files != null)
            for (File value : files) {
                //don't count dir
                if (!value.isDirectory()) {
                    String path = value.toURI().toString().toLowerCase();
                    if (path.contains(extension)) {
                        count++;
                    }
                }

                if (value.isDirectory()) {
                    getIt(value.getAbsoluteFile());
                }
            }
        return count;
    }

    public static int get(File directory, String extension) {
        extension = extension.toLowerCase();

        if (extension.isEmpty()) {
            return getIt(directory);
        } else {
            return getExtension(directory, extension);
        }
    }
}
