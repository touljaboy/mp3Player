package org.example.mp3Player.player;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaPlayer.Status;
import org.example.mp3Player.mp3.Mp3Song;

import java.io.File;

public class Mp3Player {
    private ObservableList<Mp3Song> songList;
    private Media media;
    private MediaPlayer mediaPlayer;

    public Mp3Player(ObservableList<Mp3Song> songList) {
        this.songList = songList;
    }

    public MediaPlayer getMediaPlayer() {
        return mediaPlayer;
    }

    public void play() {
        if(mediaPlayer!=null && mediaPlayer.getStatus() == Status.READY || mediaPlayer.getStatus() == Status.PAUSED) {
            mediaPlayer.play();
        }
    }
    public void stop() {
        if(mediaPlayer!=null && mediaPlayer.getStatus() == Status.PLAYING) {
            mediaPlayer.pause();
        }
    }

    public double getLoadedSongDuration() {
        if (media != null) {
            return media.getDuration().toSeconds();
        } else return 0;
    }

    public void setVolume(double volume) {
        if (mediaPlayer != null)
            mediaPlayer.setVolume(volume);
    }

    public void loadSong(int index) {
        if(mediaPlayer != null && mediaPlayer.getStatus() == Status.PLAYING)
            mediaPlayer.pause();
        Mp3Song mp3s = songList.get(index);
        media = new Media(new File(mp3s.getFilePath()).toURI().toString());
        mediaPlayer = new MediaPlayer(media);
        mediaPlayer.statusProperty().addListener((observableValue, oldVal, newVal) -> {
            if(newVal == Status.READY)
                mediaPlayer.setAutoPlay(true);
        });
    }

}
