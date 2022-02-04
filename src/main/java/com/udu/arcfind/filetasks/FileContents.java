package com.udu.arcfind.filetasks;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class FileContents {
    // this returns the text in a file
    public static ArrayList<String> get(File file) {
        try {
            if (!file.canRead()) {
                ArrayList<String> list = new ArrayList<>();

                //add a line from the file to the arraylist
                list.add("This file was denied permission to read. (arcfind)");

                return list;
            }
            Scanner s = new Scanner(file);
            ArrayList<String> list = new ArrayList<>();

            //add a line from the file to the arraylist
            while (s.hasNextLine()){
                list.add(s.nextLine());
            }
            s.close();

            return list;
        } catch (FileNotFoundException e) {
            //return null if file is not found
            e.printStackTrace();
            return null;
        }
    }
}

