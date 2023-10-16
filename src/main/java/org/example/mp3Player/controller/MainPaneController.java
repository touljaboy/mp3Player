package org.example.mp3Player.controller;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.example.mp3Player.mp3.Mp3Parser;
import org.example.mp3Player.mp3.Mp3Song;
import org.example.mp3Player.player.Mp3Player;
import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import java.io.File;
import java.io.IOException;

public class MainPaneController {
    @FXML
    private MenuPaneController menuPaneController;
    @FXML
    private ContentPaneController contentPaneController;
    @FXML
    private ControlPaneController controlPaneController;

    private Mp3Player player;

    public void initialize() {
        createPlayer();
        configureTableClick();
        configureButtons();
        configureMenu();
    }




    private void createPlayer() {
        ObservableList<Mp3Song> songList = contentPaneController.getContentTable().getItems();
        player = new Mp3Player(songList);
    }

    private void configureTableClick() {
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        contentTable.addEventHandler(MouseEvent.MOUSE_CLICKED, event -> {
            if (event.getClickCount() == 2) {
                int selectedIndex = contentTable.getSelectionModel().getSelectedIndex();
                playSelectedSong(selectedIndex);
            }
        });
    }

    private void playSelectedSong(int selectedIndex) {
        player.loadSong(selectedIndex);
        configureProgressBar();
        configureVolume();
        controlPaneController.getPlayButton().setSelected(true);
    }


    private void configureProgressBar() {
        Slider progressSlider = controlPaneController.getProgressSlider();
        //ustawienie długości suwaka postępu
        player.getMediaPlayer().setOnReady(() -> progressSlider.setMax(player.getLoadedSongDuration()));
        //zmiana czasu w odtwarzaczu automatycznie będzie aktualizowała suwak
        player.getMediaPlayer().currentTimeProperty().addListener((arg, oldVal, newVal) ->
                progressSlider.setValue(newVal.toSeconds()));
        //przesunięcie suwaka spowoduje przewinięcie piosenki do wskazanego miejsca
        progressSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            if(progressSlider.isValueChanging()) {
                player.getMediaPlayer().seek(Duration.seconds(newValue.doubleValue()));
            }
    });
    }



    private void configureVolume() {
        Slider volumeSlider = controlPaneController.getVolumeSlider();
        volumeSlider.valueProperty().unbind();
        volumeSlider.setMax(1.0);
        volumeSlider.valueProperty().bindBidirectional(player.getMediaPlayer().volumeProperty());
    }

    private void configureButtons() {
        TableView<Mp3Song> contentTable = contentPaneController.getContentTable();
        ToggleButton playButton = controlPaneController.getPlayButton();
        Button previousButton = controlPaneController.getPreviousButton();
        Button nextButton = controlPaneController.getNextButton();

        playButton.setOnAction(action ->
        {
            if (playButton.isSelected())
                player.play();
            else player.stop();
        });
        nextButton.setOnAction(actionEvent -> {
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() + 1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });
        previousButton.setOnAction(actionEvent -> {
            contentTable.getSelectionModel().select(contentTable.getSelectionModel().getSelectedIndex() - 1);
            playSelectedSong(contentTable.getSelectionModel().getSelectedIndex());
        });
    }

    private void configureMenu() {
        MenuItem openFile = menuPaneController.getFileMenuItem();
        MenuItem dirMenuItem = menuPaneController.getDirMenuItem();

        openFile.setOnAction(actionEvent -> {
            FileChooser fc = new FileChooser();
            fc.getExtensionFilters().add(new FileChooser.ExtensionFilter("Mp3", "*.mp3"));
            File file = fc.showOpenDialog(new Stage());
            try {
                contentPaneController.getContentTable().getItems().add(Mp3Parser.createMp3Song(file));
                showMessage("Załadowano plik: " +file.getName());
            } catch (TagException | IOException e) {
                showMessage("Nie można otworzyć pliku: " +file.getName());
            }
        });
        dirMenuItem.setOnAction(actionEvent -> {
            DirectoryChooser dc = new DirectoryChooser();
            File dir = dc.showDialog(new Stage());
            try {
                contentPaneController.getContentTable().getItems().addAll(Mp3Parser.createMp3List(dir));
                showMessage("Załadowano folder: " +dir.getName());
            } catch (TagException | IOException e) {
                showMessage("Nie można otworzyć folderu");
            }
        });
    }

    private void showMessage(String message) {
        controlPaneController.getMessageTextField().setText(message);
    }


}

