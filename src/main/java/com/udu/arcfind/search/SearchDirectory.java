package com.udu.arcfind.search;

import com.udu.arcfind.Arcfind;
import com.udu.arcfind.filetasks.DirectoryContents;

import java.util.ArrayList;

public class SearchDirectory {
    //this will put <searchingString> to make it easier to see
    public static String addIndicators(String str, String indicate) {
        StringBuilder strFix = new StringBuilder(str);

        int numOfOccurrences = str.split(indicate,  - 1).length - 1; //int of how many indicate in str
        int indicateIndex = strFix.indexOf(indicate); //int of indicate index

        int sizeOfIndicate = indicate.length();

        if (numOfOccurrences > 1) {
            int count = 0;
            do { //replace indicate with ->indicate<- on each indicate in str
                strFix.insert(indicateIndex, "##");
                strFix.insert(indicateIndex + sizeOfIndicate + 2, "##");

                indicateIndex = strFix.indexOf(indicate, indicateIndex + 3);
                count++;
            } while (count != numOfOccurrences);
        } else if (numOfOccurrences > 0) {  //replace indicate with ->indicate<- in str
            strFix.insert(indicateIndex, "##");
            strFix.insert(indicateIndex + sizeOfIndicate + 2, "##");
        }

        return strFix.toString();
    }

    //this is like String.indexOf(String str, Int fromIndex), but for ArrayLists
    public static int indexOf(ArrayList<String> list, String str, int fromIndex) {
        int index = -1;

        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).contains(str)) {
                index = i;

                if (i > fromIndex) {
                    return index;
                }
            }
        }

        return index;
    }

    //this  would return the number of matching elements in an arraylist
    static int numberOfMatchingElements(ArrayList<String> arr, String match) {
        int finalInt = 0;

        for (String currentElement : arr) {
            if (currentElement.contains(match)) finalInt++;
        }

        return finalInt;
    }

    //this would return an arraylist with the following pattern below
    //a - matching line
    //b - file path
    //this would then continue
    public static ArrayList<String> search() {
        ArrayList<String> directoryContents = DirectoryContents.get(Arcfind.directory, Arcfind.ignoreFile, Arcfind.ignoreDirectory, Arcfind.searchInSubdirectories, Arcfind.searchInArchives);

        ArrayList<String> returningList = new ArrayList<>();

        if (directoryContents == null) {
            System.out.println("file path given does not exist/is wrong");
            return null;
        }

        int numberOfMatchingIndexes = numberOfMatchingElements(directoryContents, Arcfind.searchingString);

        int temp = 0;

        //for each index that is matching
        for (int i=0; i<numberOfMatchingIndexes; i++) {
            int currentElementIndex = indexOf(directoryContents, Arcfind.searchingString, temp);

            //make sure it does not contain the file path
            if (!directoryContents.get(currentElementIndex).contains(Arcfind.uuidSeperator)) {
                // adds <matching> to the string
                String addIndicators = addIndicators(directoryContents.get(currentElementIndex), Arcfind.searchingString);

                returningList.add(addIndicators); //add matching line to returningList

                int offset = 0;

                //while current element index does not contain uuid
                while (!directoryContents.get(currentElementIndex - offset).contains(Arcfind.uuidSeperator)) {
                    offset++;
                    //if current index contains the uuid/path
                    if (directoryContents.get(currentElementIndex - offset).contains(Arcfind.uuidSeperator)) {
                        int indexOfUUIDSpace = directoryContents.get(currentElementIndex - offset).indexOf(" ") + 1;
                        String filePath = directoryContents.get(currentElementIndex - offset).substring(indexOfUUIDSpace);

                        int indexOfSlash = filePath.lastIndexOf(Arcfind.osPathSeperator);

                        String fileName = filePath.substring(indexOfSlash + 1);

                        returningList.add(fileName);
                        returningList.add(filePath);
                        break;
                    }
                }
            }
            temp = currentElementIndex;
        }

        //set the arrayList for other classes to use
        Arcfind.contentsOfDir = directoryContents;

        return returningList;
    }
}
