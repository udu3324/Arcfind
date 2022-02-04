package com.udu.arcfind.archive;

import com.udu.arcfind.Arcfind;
import com.udu.arcfind.filetasks.DirectoryContents;
import com.udu.arcfind.resources.TempStorage;
import org.rauschig.jarchivelib.ArchiveFormat;
import org.rauschig.jarchivelib.Archiver;
import org.rauschig.jarchivelib.ArchiverFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.udu.arcfind.resources.TempStorage.file;

public class ZipContents {
    //gets the files in a zip and gets the contents of them
    public static ArrayList<String> get(File zip) {
        System.out.println("ZipContents Status: Currently Extracting " + zip);

        //clear tempStorage for new things
        TempStorage.clear();

        //unzip
        try {
            Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.ZIP);
            archiver.extract(zip, file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //embed filename to arraylist of starting directory contents or else you will get UUID tempStorage\(filename)
        ArrayList<String> bypass = new ArrayList<>();
        bypass.add(Arcfind.uuidSeperator + " " + zip);

        //then read files in directory to arraylist and return contents
        ArrayList<String> finalContents = DirectoryContents.get(file, null, bypass, true, false);

        //clear tempStorage
        TempStorage.clear();

        System.out.println("ZipContents Status: Done Extracting " + zip);
        return finalContents;
    }
}

