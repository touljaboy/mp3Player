package org.example.mp3Player.controller;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class MenuPaneController {

    @FXML
    private MenuItem aboutMenuItem;

    @FXML
    private MenuItem closeMenuItem;

    @FXML
    private MenuItem dirMenuItem;

    @FXML
    private MenuItem fileMenuItem;

    public MenuItem getAboutMenuItem() {
        return aboutMenuItem;
    }

    public MenuItem getCloseMenuItem() {
        return closeMenuItem;
    }

    public MenuItem getDirMenuItem() {
        return dirMenuItem;
    }

    public MenuItem getFileMenuItem() {
        return fileMenuItem;
    }

    public void initialize(){
        closeMenuItem.setOnAction(actionEvent -> Platform.exit());

        aboutMenuItem.setOnAction(actionEvent -> {
            try {
                Parent parent = FXMLLoader.load(getClass().getResource("/fxml/aboutPane.fxml"));
                Scene scene = new Scene(parent);
                Stage stage = new Stage();
                stage.setScene(scene);
                stage.setTitle("Epic mp3 - about");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace(); //ignore
            }
        });
    }


}
