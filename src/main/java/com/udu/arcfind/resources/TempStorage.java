package com.udu.arcfind.resources;

import java.io.File;
import java.nio.file.Paths;

public class TempStorage {
    public static File file = new File("tempStorage");

    public static void delete(File directoryToBeDeleted) {
        File[] allContents = directoryToBeDeleted.listFiles();
        if (allContents != null) {
            for (File file : allContents) {
                delete(file);
            }
        }
        if (directoryToBeDeleted.delete()) {
            System.out.println("tempStorage has successfully been deleted");
        } else {
            System.out.println("something wrong has happened and tempStorage couldn't be removed");
        }
    }

    //creates tempStorage dir
    public static void create() {
        //check if dir does not exist first
        if (!file.exists()){
            if (file.mkdirs()) {
                System.out.println("Temp Storage has been created. " + Paths.get(file.toURI()));
            }
        }
    }

    //clears the contents of tempStorage dir
    public static void clear() {
        //check if dir exists
        if (file.exists()){
            delete(file);
            System.out.println("Temp storage has been cleared. " + Paths.get(file.toURI()));
            create();
        }
    }

    //removes/deletes tempStorage
    public static void remove() {
        //check if dir exists
        if (file.exists()){
            System.out.println("Temp storage has been removed. " + Paths.get(file.toURI()));
            delete(file);
        }
    }
}

