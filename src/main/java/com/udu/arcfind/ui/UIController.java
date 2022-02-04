package com.udu.arcfind.ui;

import com.udu.arcfind.Arcfind;
import com.udu.arcfind.filetasks.DirectoryFileCount;
import com.udu.arcfind.search.SearchDirectory;
import com.udu.arcfind.show.FileContents;
import com.udu.arcfind.ui.table.FileDisplay;
import com.udu.arcfind.ui.table.FoundFile;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.DirectoryChooser;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;

public class UIController {
    @FXML
    public Button fileExplorerButton;
    public TextField directoryInput;

    public TextField searchInput;

    public CheckBox subdirectoryBoolean;
    public CheckBox archiveBoolean;

    public TableView<FoundFile> foundLinesTable;
    public TableColumn<Object, Object> foundLinesTableMatchingLines;
    public TableColumn<Object, Object> foundLinesTableFileName;
    public TableColumn<Object, Object> foundLinesTableFilePath;

    public TableView<FileDisplay> fileTable;
    public TableColumn<Object, Object> fileTableNumber;
    public TableColumn<Object, Object> fileTableFileName;

    public CheckBox fileMaskBoolean;
    public TextField fileMaskInput;

    public Button searchButton;
    public Button githubButton;
    public Button openSelectedFileButton;

    public TextField getDirectoryInput() {
        return directoryInput;
    }
    public TextField getSearchInput() {
        return searchInput;
    }
    public TextField getFileMaskInput() {
        return fileMaskInput;
    }
    public CheckBox getSubdirectoryBoolean() {
        return subdirectoryBoolean;
    }
    public CheckBox getArchiveBoolean() {
        return archiveBoolean;
    }
    public CheckBox getFileMaskBoolean() {
        return fileMaskBoolean;
    }
    public Button getSearchButton() {
        return searchButton;
    }
    public Button getFileExplorerButton() {
        return fileExplorerButton;
    }
    public TableView<FoundFile> getFoundLinesTable() {
        return foundLinesTable;
    }
    public TableColumn<Object, Object> getFoundLinesTableFileName() {
        return foundLinesTableFileName;
    }
    public TableColumn<Object, Object> getFoundLinesTableMatchingLines() {
        return foundLinesTableMatchingLines;
    }
    public TableColumn<Object, Object> getFoundLinesTableFilePath() {
        return foundLinesTableFilePath;
    }
    public TableView<FileDisplay> getFileTable() {
        return fileTable;
    }
    public TableColumn<Object, Object> getFileTableNumber() {
        return fileTableNumber;
    }
    public TableColumn<Object, Object> getFileTableFileName() {
        return fileTableFileName;
    }
    public Button getOpenSelectedFileButton() {
        return openSelectedFileButton;
    }

    //disable/enable searching tools
    public void disableSearchingTools(Boolean bool) {
        //do this to allow variables to be set in the right place
        getFoundLinesTableMatchingLines().setCellValueFactory(new PropertyValueFactory<>("matchingLine"));
        getFoundLinesTableFileName().setCellValueFactory(new PropertyValueFactory<>("fileName"));
        getFoundLinesTableFilePath().setCellValueFactory(new PropertyValueFactory<>("filePath"));

        //disable stuff while searching
        getSearchButton().setDisable(bool);
        getFileExplorerButton().setDisable(bool);
        getSubdirectoryBoolean().setDisable(bool);
        getArchiveBoolean().setDisable(bool);
        getFileMaskBoolean().setDisable(bool);
        getDirectoryInput().setDisable(bool);
        getSearchInput().setDisable(bool);
        getFileMaskInput().setDisable(bool);
    }
    //dialogs n stuff
    public static void createError(String errorMSG) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setContentText(errorMSG);
        alert.initOwner(StartUI.sharingStage);

        alert.showAndWait();
    }

    //file display
    public void displayFileOnTable(File file) {
        Arcfind.openSelectedFile = file;

        //do this to allow variables to be set in the right place
        getFileTableNumber().setCellValueFactory(new PropertyValueFactory<>("fileNumber"));
        getFileTableFileName().setCellValueFactory(new PropertyValueFactory<>("fileLine"));

        //clear table for new results
        getFileTable().getItems().clear();
        getFileTableFileName().setText("File Path");

        //enable button to open file if it's not an archive
        getOpenSelectedFileButton().setDisable(!file.exists());

        ArrayList<String> fileContents = FileContents.get(file);
        boolean doOnce = true;
        for (int i = 0; i < fileContents.size(); i = i + 2) {
            if (doOnce) {
                getFileTableFileName().setText(String.valueOf(file));
                doOnce = false;
            }

            String fileNumber = fileContents.get(i); //a
            String fileLine = SearchDirectory.addIndicators(fileContents.get(i + 1), Arcfind.searchingString); //b

            getFileTable().getItems().add(new FileDisplay(fileNumber, fileLine));
        }
    }

    //listeners
    public void onEnter(ActionEvent ae){
        Object source = ae.getSource();

        if (source instanceof TextField textField) {
            if (textField.getId().equals(getDirectoryInput().getId())) { //directoryInput

                String dir = getDirectoryInput().getText();
                System.out.println("text of directoryInput is " + dir);
                Arcfind.directory = new File(dir);

            } else if (textField.getId().equals(getSearchInput().getId())) { //searchInput

                String text = getSearchInput().getText();
                System.out.println("text of searchInput is " + text);
                Arcfind.searchingString = text;

            } else if (textField.getId().equals(getFileMaskInput().getId())) { //fileMaskInput

                String text = getFileMaskInput().getText();
                System.out.println("text of fileMaskInput is " + text);
                Arcfind.fileMaskExtension = text;
            }
        }
    }

    public void onFileMaskCheckBoxClick() {
        Arcfind.fileMask = getFileMaskBoolean().isSelected();
        System.out.println("fileMask bool is set to " + Arcfind.fileMask);
    }

    public void onArchivesCheckBoxClick() {
        Arcfind.searchInArchives = getArchiveBoolean().isSelected();
        System.out.println("searchInArchives bool is set to " + Arcfind.searchInArchives);
    }

    public void onSubdirectoriesCheckBoxClick() {
        Arcfind.searchInSubdirectories = getSubdirectoryBoolean().isSelected();
        System.out.println("searchInSubdirectories bool is set to " + Arcfind.searchInSubdirectories);
    }

    public void onFileExplorerButton() {
        System.out.println("File explorer button has been clicked on");

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose a Directory/Folder");
        if (!(Arcfind.directory == null)) {
            if (Arcfind.directory.exists()) {
                chooser.setInitialDirectory(Arcfind.directory);
            }
        }
        File selectedDirectory = chooser.showDialog(StartUI.sharingStage);
        if (selectedDirectory != null) {
            String pathOfDirectory = selectedDirectory.toString();
            System.out.println("pathOfDirectory has been set to " + pathOfDirectory);

            getDirectoryInput().setText(pathOfDirectory);

            Arcfind.directory = selectedDirectory;
        }
    }

    public void onSearchButton() {
        boolean canMoveOn = true; //alternative to return;

        System.out.println("Search button has been clicked on");

        //set directory from the input
        String dir = getDirectoryInput().getText();
        System.out.println("text of directoryInput is " + dir);
        Arcfind.directory = new File(dir);

        //set searchingString from the input
        String searchingStr = getSearchInput().getText();
        System.out.println("text of searchInput is " + searchingStr);
        Arcfind.searchingString = searchingStr;

        //check if directory exists/is set
        if (!Arcfind.directory.exists()) {
            System.out.println("directory isn't set or does not exist");
            createError("The directory isn't set/doesn't exist.");
            canMoveOn = false;
        } else if (Arcfind.searchingString.isEmpty()) { //check if search for is set
            System.out.println("searching string isn't set");
            createError("The searching string isn't set.");
            canMoveOn = false;
        }

        //file mask optional
        if (Arcfind.fileMask) {
            String fME = getFileMaskInput().getText();
            System.out.println("text of fileMaskInput is " + fME);
            Arcfind.fileMaskExtension = fME;

            if (Arcfind.fileMaskExtension.isEmpty()) { //check if search for is set
                System.out.println("file mask extension isn't set");
                createError("The file mask extension isn't set.");
                canMoveOn = false;
            }
        }

        if (canMoveOn) {

            //clear table for new results
            getFoundLinesTable().getItems().clear();

            disableSearchingTools(true);

            //add status to table
            getFoundLinesTable().getItems().add(new FoundFile("Currently searching through files. This might be slow if there's a lot of archives.", "(arcfind)", ""));
            getFoundLinesTable().getItems().add(new FoundFile("Total Files Count: " + DirectoryFileCount.get(Arcfind.directory, ""), "(arcfind)", ""));
            int gzCount = DirectoryFileCount.get(Arcfind.directory, "gz");
            int jarCount = DirectoryFileCount.get(Arcfind.directory, "jar");
            int szCount = DirectoryFileCount.get(Arcfind.directory, "7z");
            int zipCount = DirectoryFileCount.get(Arcfind.directory, "zip");
            if (gzCount > 0) {getFoundLinesTable().getItems().add(new FoundFile("Total Gzip Count: " + gzCount, "(arcfind)", ""));}
            if (jarCount > 0) {getFoundLinesTable().getItems().add(new FoundFile("Total Jar Count: " + jarCount, "(arcfind)", ""));}
            if (szCount > 0) {getFoundLinesTable().getItems().add(new FoundFile("Total 7z Count: " + szCount, "(arcfind)", ""));}
            if (zipCount > 0) {getFoundLinesTable().getItems().add(new FoundFile("Total Zip Count: " + zipCount, "(arcfind)", ""));}

            //thread to not interrupt ui
            Thread thread = new Thread(() -> {
                ArrayList<String> foundLines = SearchDirectory.search();

                //clear table for new results
                getFoundLinesTable().getItems().clear();

                if (foundLines == null) {
                    getFoundLinesTable().getItems().clear();
                    getFoundLinesTable().getItems().add(new FoundFile("Uh oh! Something very wrong happened! This occurred while trying to search in the directory.", "(arcfind)", ""));
                } else if (foundLines.isEmpty()) {
                    getFoundLinesTable().getItems().clear();
                    getFoundLinesTable().getItems().add(new FoundFile("Nothing was found. ", "(arcfind)", ""));
                } else {
                    boolean doOnce = true;
                    //for each found line in array
                    for (int i = 0; i < foundLines.size(); i = i + 3) {
                        if (doOnce) {
                            disableSearchingTools(false);

                            //add event listener when clicking on rows
                            getFoundLinesTable().setOnMousePressed(event -> {
                                if (event.isPrimaryButtonDown() && event.getClickCount() == 1) {
                                    String filePath = getFoundLinesTable().getSelectionModel().getSelectedItem().getFilePath();

                                    displayFileOnTable(Paths.get(filePath).toFile());
                                }
                            });

                            doOnce = false;
                        }

                        String currentLine = foundLines.get(i); //a
                        String currentFileName = foundLines.get(i + 1); //b
                        String currentFilePath = foundLines.get(i + 2); //c

                        //file mask only add to table if it matches extension given
                        if (Arcfind.fileMask) {
                            if (currentFileName.toLowerCase().contains(Arcfind.fileMaskExtension.toLowerCase())) {
                                getFoundLinesTable().getItems().add(new FoundFile(currentLine, currentFileName, currentFilePath));
                            }
                        } else {
                            getFoundLinesTable().getItems().add(new FoundFile(currentLine, currentFileName, currentFilePath));
                        }
                    }
                }

                disableSearchingTools(false);
            });

            thread.start();
        }
    }

    public void onGithubButtonClick() {
        if (Desktop.isDesktopSupported() && Desktop.getDesktop().isSupported(Desktop.Action.BROWSE)) {
            try {
                Desktop.getDesktop().browse(new URI("https://github.com/udu3324"));
            } catch (IOException | URISyntaxException e) {
                e.printStackTrace();
            }
        }
    }

    public void onSelectedFileOpenClick() {
        Desktop desktop;

        File file = Arcfind.openSelectedFile;

        if (!file.exists()) {
            if (file.toString().isEmpty()) {
                file = new File("no file path (arcfind)");
            }

            createError("File could not be opened. Here's why: \n" +
                    " - It is a archive \n" +
                    " - No file was selected \n" +
                    " - The file was removed \n" +
                    "Here's the path to it: " + file);
            return;
        }

        try {
            if (Desktop.isDesktopSupported()) {
                desktop = Desktop.getDesktop();
                desktop.open(file);
            } else {
                System.out.println("desktop is not supported");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}