package com.udu.arcfind.ui;

import com.udu.arcfind.Arcfind;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.scene.image.Image;

import java.io.IOException;
import java.util.Objects;

public class StartUI extends Application{
    public static Stage sharingStage;

    private void closeWindowEvent(WindowEvent event) {
        System.out.println("Window close request");
        System.exit(0);
    }

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(Arcfind.class.getResource("hello-view.fxml"));

        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Arcfind v1.0");

        sharingStage = stage;

        //listener to make window transparent when not focused
        stage.focusedProperty().addListener((ov, onHidden, onShown) -> {
            if (onHidden) {
                stage.setOpacity(.7);
            } else if (onShown) {
                stage.setOpacity(1);
            }
        });

        stage.setResizable(false);
        stage.setAlwaysOnTop(true);
        stage.setScene(scene);
        stage.getIcons().add(new Image(Objects.requireNonNull(Arcfind.class.getResourceAsStream("arcfind-logo.png"))));
        scene.getStylesheets().add(String.valueOf(Arcfind.class.getResource("dark-mode.css")));
        stage.getScene().getWindow().addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, this::closeWindowEvent);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}