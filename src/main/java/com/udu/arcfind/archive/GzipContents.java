package com.udu.arcfind.archive;

import com.udu.arcfind.Arcfind;
import com.udu.arcfind.filetasks.DirectoryContents;
import com.udu.arcfind.resources.TempStorage;
import org.rauschig.jarchivelib.Compressor;
import org.rauschig.jarchivelib.CompressorFactory;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static com.udu.arcfind.resources.TempStorage.file;

public class GzipContents {
    //gets the files in a gzip and gets the contents of them
    public static ArrayList<String> get(File gzip) {
        System.out.println("GzipContents Status: Currently Extracting " + gzip);

        //clear tempStorage for new things
        TempStorage.clear();

        //decompress gzip to tempStorage
        try {
            Compressor compressor = CompressorFactory.createCompressor(gzip);
            compressor.decompress(gzip, file);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        //embed filename to arraylist of starting directory contents or else you will get UUID tempStorage\(filename)
        ArrayList<String> bypass = new ArrayList<>();
        bypass.add(Arcfind.uuidSeperator + " " + gzip);

        //then read files in directory to arraylist and return contents
        ArrayList<String> finalContents = DirectoryContents.get(file, null, bypass, true, false);

        //clear tempStorage
        TempStorage.clear();

        System.out.println("GzipContents Status: Done Extracting " + gzip);
        return finalContents;
    }
}

