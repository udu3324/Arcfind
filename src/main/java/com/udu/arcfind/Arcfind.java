package com.udu.arcfind;

import com.udu.arcfind.resources.ShutdownHook;
import com.udu.arcfind.resources.TempStorage;
import com.udu.arcfind.ui.StartUI;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

public class Arcfind {
    //thank you!! - https://stackoverflow.com/a/52654791/14677066
    //run mvn package to create a fatjar

    public static String osPathSeperator = "";

    public static ArrayList<String> contentsOfDir; //this will be set by SearchDir
    public static File directory;
    public static String searchingString;
    public static String fileMaskExtension;
    public static Boolean fileMask = false;
    public static Boolean searchInSubdirectories = true;
    public static Boolean searchInArchives = true;
    public static File openSelectedFile;

    //todo add ignore file and ignore directory to search controls
    public static ArrayList<String> ignoreFile = new ArrayList<>();
    public static ArrayList<String> ignoreDirectory = new ArrayList<>();


    public static String uuidSeperator = UUID.randomUUID().toString().replace("-", "");

    public static void main(String[] args) {
        //create ui in a thread to not interrupt code below
        new Thread(() -> StartUI.main(args)).start();

        //create tempStorage
        TempStorage.create();

        //create shutdown hook to remove tempStorage
        Runtime.getRuntime().addShutdownHook(new Thread(new ShutdownHook()));

        //set os specific slash in file paths
        osPathSeperator = System.getProperty("file.separator");
    }
}
