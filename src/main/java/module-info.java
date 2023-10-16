module mp3Player {
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.fxml;
    requires jid3lib;
    requires javafx.media;

    opens org.example.mp3Player.controller to javafx.fxml;
    opens org.example.mp3Player.mp3 to javafx.base;
    exports org.example.mp3Player.main to javafx.graphics;
}