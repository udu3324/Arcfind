package com.udu.arcfind.ui.table;

import javafx.beans.property.SimpleStringProperty;

public class FoundFile {
    SimpleStringProperty matchingLine;
    SimpleStringProperty fileName;
    SimpleStringProperty filePath;

    public FoundFile(String matchingLine, String fileName, String filePath) {
        this.matchingLine = new SimpleStringProperty(matchingLine);
        this.fileName = new SimpleStringProperty(fileName);
        this.filePath = new SimpleStringProperty(filePath);
    }

    public String getMatchingLine(){
        return matchingLine.get();
    }
    public void setMatchingLine(String matching){
        matchingLine.set(matching);
    }
    public String getFileName() {
        return fileName.get();
    }
    public void setFileName(String fileName) {
        this.fileName.set(fileName);
    }
    public String getFilePath(){
        return filePath.get();
    }
    public void setFilePath(String file){
        filePath.set(file);
    }
}