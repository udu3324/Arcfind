package com.udu.arcfind.show;

import com.udu.arcfind.Arcfind;

import java.io.File;
import java.util.ArrayList;

public class FileContents {
    public static ArrayList<String> get(File file) {
        ArrayList<String> finalList = new ArrayList<>();

        String fileSeperator = Arcfind.uuidSeperator + " " + file;

        int indexOfFileSeperator = Arcfind.contentsOfDir.indexOf(fileSeperator);
        int indexOfLastFileSeperator = 0;

        int offsetIndex = 1;
        int sizeOfDirContents = Arcfind.contentsOfDir.size();

        while (!Arcfind.contentsOfDir.get(indexOfFileSeperator + offsetIndex).contains(Arcfind.uuidSeperator)) {
            offsetIndex++;

            //if current index is the last index (in case if it's the last file in the array)
            if (offsetIndex + indexOfFileSeperator == sizeOfDirContents) {
                indexOfLastFileSeperator = indexOfFileSeperator + offsetIndex;
                break;
            }
            if (Arcfind.contentsOfDir.get(indexOfFileSeperator + offsetIndex).contains(Arcfind.uuidSeperator)) {
                indexOfLastFileSeperator = indexOfFileSeperator + offsetIndex;
                break;
            }
        }

        int startingIndexElement = indexOfFileSeperator + 1;
        int endingIndexElement = indexOfLastFileSeperator;

        int lineNumber = 1;

        //add the elements to array
        for (int i=startingIndexElement; i<endingIndexElement; i++) {
            finalList.add(lineNumber + "");
            finalList.add(Arcfind.contentsOfDir.get(i));
            //a - line number
            //b - line
            lineNumber++;
        }

        return finalList;
    }
}
