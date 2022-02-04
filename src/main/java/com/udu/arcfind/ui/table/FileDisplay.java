package com.udu.arcfind.ui.table;

import javafx.beans.property.SimpleStringProperty;

public class FileDisplay {
    SimpleStringProperty fileNumber;
    SimpleStringProperty fileLine;

    public FileDisplay(String fileNumber, String fileLine) {
        this.fileNumber = new SimpleStringProperty(fileNumber);
        this.fileLine = new SimpleStringProperty(fileLine);
    }

    public String getFileLine() {
        return fileLine.get();
    }
    public String getFileNumber() {
        return fileNumber.get();
    }
    public void setFileLine(String fileLine) {
        this.fileLine.set(fileLine);
    }
    public void setFileNumber(String fileNumber) {
        this.fileNumber.set(fileNumber);
    }
}

