package org.example.mp3Player.mp3;

import java.util.Objects;

public class Mp3Song {
    private String title;
    private String author;
    private String album;
    private String filePath;

    public Mp3Song(String title, String author, String album, String filePath) {
        this.title = title;
        this.author = author;
        this.album = album;
        this.filePath = filePath;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getFilePath() {
        return filePath;
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Mp3Song mp3Song = (Mp3Song) o;
        return Objects.equals(title, mp3Song.title) && Objects.equals(author, mp3Song.author) && Objects.equals(album, mp3Song.album) && Objects.equals(filePath, mp3Song.filePath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, author, album, filePath);
    }

    @Override
    public String toString() {
        return "Mp3Song{" +
                "title: " + title +
                ", author: " + author +
                ", album: " + album +
                ", filePath: " + filePath +
                '}';
    }
}
