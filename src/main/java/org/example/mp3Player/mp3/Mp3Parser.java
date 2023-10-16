package org.example.mp3Player.mp3;

import org.farng.mp3.MP3File;
import org.farng.mp3.TagException;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Mp3Parser {
    public static Mp3Song createMp3Song(File file) throws TagException, IOException {
        MP3File mp3File = new MP3File(file);
        String absolutePath = file.getAbsolutePath();
        String songTitle = mp3File.getID3v2Tag().getSongTitle();
        String leadArtist = mp3File.getID3v2Tag().getLeadArtist();
        String albumTitle = mp3File.getID3v2Tag().getAlbumTitle();
        return new Mp3Song(songTitle, leadArtist, albumTitle, absolutePath);
    }

    public static List<Mp3Song> createMp3List(File dir) throws TagException, IOException {
        if (!dir.isDirectory())
            throw new IllegalArgumentException("Not a directory");
        List<Mp3Song> mp3List = new ArrayList<>();
        File[] files = dir.listFiles();
        for (File file : files) {
            String fileExtension = file.getName().substring(file.getName().lastIndexOf(".") + 1);
            if(fileExtension.equals("mp3"))
                mp3List.add(createMp3Song(file));
        }
        return mp3List;
    }
}
