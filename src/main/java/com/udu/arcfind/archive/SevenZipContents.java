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

public class SevenZipContents {
    //gets the files in a 7zip and gets the contents of them
    public static ArrayList<String> get(File sevenZip) {
        System.out.println("7zipContents Status: Currently Extracting " + sevenZip);

        //clear tempStorage for new things
        TempStorage.clear();

        //unzip
        try {
            Archiver archiver = ArchiverFactory.createArchiver(ArchiveFormat.SEVEN_Z);
            archiver.extract(sevenZip, file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //embed filename to arraylist of starting directory contents or else you will get UUID tempStorage\(filename)
        ArrayList<String> bypass = new ArrayList<>();
        bypass.add(Arcfind.uuidSeperator + " " + sevenZip);

        //then read files in directory to arraylist and return contents
        ArrayList<String> finalContents = DirectoryContents.get(file, null, bypass, true, false);

        //clear tempStorage
        TempStorage.clear();

        System.out.println("7zipContents Status: Done Extracting " + sevenZip);
        return finalContents;
    }
}