package com.udu.arcfind.filetasks;

import com.udu.arcfind.Arcfind;
import com.udu.arcfind.archive.GzipContents;
import com.udu.arcfind.archive.JarContents;
import com.udu.arcfind.archive.SevenZipContents;
import com.udu.arcfind.archive.ZipContents;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.regex.Pattern;

public class DirectoryContents {
    // this returns text in a file in a directory of directories
    public static ArrayList<String> get(File directory, ArrayList<String> ignoreFile, ArrayList<String> ignoreDirectory, Boolean searchOtherDirs, Boolean searchInArchives) {
        if (!directory.exists()) {
            System.out.println("Directory given does not exist!");
            return null;
        }

        // fix optional parameters if entered as null
        if (ignoreFile == null) {
            ignoreFile = new ArrayList<>();
            ignoreFile.add("");
        }
        if (ignoreDirectory == null) {
            ignoreDirectory = new ArrayList<>();
            ignoreDirectory.add("");
        }

        //this is for if the directory/file working on is an archive
        boolean isArchiveDirectory = false;
        String archiveAddress = "";
        if (!ignoreDirectory.isEmpty()) { //prevent outOfBounds exception
            if (ignoreDirectory.get(0).contains(Arcfind.uuidSeperator)) {
                //if it does, this means that this directory is an archive directory
                isArchiveDirectory = true;
                archiveAddress = ignoreDirectory.get(0).substring(ignoreDirectory.get(0).indexOf(" "));
                System.out.println("archiveAddress " + archiveAddress);
            }
        }

        ArrayList<String> finalContents = new ArrayList<>();

        int numberOfFiles = Objects.requireNonNull(directory.list()).length;
        ArrayList<File> filePathsInDirectory = new ArrayList<>(Arrays.asList(Objects.requireNonNull(directory.listFiles())));

        //lol
        System.out.println("Directory is working in \"" + directory + "\" with " + numberOfFiles + " file(s).");

        for (int i=0; i<numberOfFiles; i++) { //for each file
            File currentObj = filePathsInDirectory.get(i);

            //stop if current file being handled is a directory (or else exception occurs)
            if (!currentObj.isDirectory() && currentObj.canRead()) {
                ArrayList<String> contentsOfCurrentFile = FileContents.get(currentObj);

                //regex to match files
                String fileExtension = currentObj.toString();

                Pattern zip = Pattern.compile("\\.zip$", Pattern.CASE_INSENSITIVE);
                Pattern gzip = Pattern.compile("\\.gzip$|\\.gz$", Pattern.CASE_INSENSITIVE);
                Pattern sevenZip = Pattern.compile("\\.7z$", Pattern.CASE_INSENSITIVE);
                Pattern jar = Pattern.compile("\\.jar$", Pattern.CASE_INSENSITIVE);

                if (zip.matcher(fileExtension).find() && searchInArchives && !ignoreFile.contains(currentObj.toString())) { //zip
                    finalContents.addAll(ZipContents.get(currentObj));
                } else if (gzip.matcher(fileExtension).find() && searchInArchives && !ignoreFile.contains(currentObj.toString())) { //gzip //gz
                    finalContents.addAll(GzipContents.get(currentObj));
                } else if (sevenZip.matcher(fileExtension).find() && searchInArchives && !ignoreFile.contains(currentObj.toString())) { //7z
                    finalContents.addAll(SevenZipContents.get(currentObj));
                } else if (jar.matcher(fileExtension).find() && searchInArchives && !ignoreFile.contains(currentObj.toString())) { //jar
                    finalContents.addAll(JarContents.get(currentObj));
                } else { //it is probably not an archive
                    assert contentsOfCurrentFile != null;

                    //don't add file to final contents if file is empty or file is directory or file is not allowed
                    if (!contentsOfCurrentFile.isEmpty() && !currentObj.isDirectory() && !ignoreFile.contains(currentObj.toString())) {

                        // add uuid separate to separate files
                        if (isArchiveDirectory) {
                            String fileName = currentObj.toString().substring(currentObj.toString().indexOf(Arcfind.osPathSeperator));


                            contentsOfCurrentFile.add(0, Arcfind.uuidSeperator + archiveAddress + fileName);
                        } else {
                            contentsOfCurrentFile.add(0, Arcfind.uuidSeperator + " " + currentObj);
                        }

                        // add contents of this file to final contents
                        finalContents.addAll(contentsOfCurrentFile);
                    }
                }

            } else if (!ignoreDirectory.contains(currentObj.toString()) && searchOtherDirs && currentObj.canRead()) { //if directory is not blacklisted and search other dirs is allowed
                // do get() again but with the directory

                finalContents.addAll(get(currentObj, ignoreFile, ignoreDirectory, true, searchInArchives));
            }
        }


        return finalContents;
    }
}