package org.geekhub.hw5;

import java.nio.file.Path;
import java.nio.file.Paths;

import static org.geekhub.hw5.FileUtils.deleteDirectories;

public class MusicDownloader {

    public static void main(String[] args) {
        Path pathToPlay = Paths.get("C:", "Programming", "Java", "GeekHub", "Homework", "java-core", "05-Exception-Files", "src", "main", "resources", "playlist.txt");
        String pathToPlaylist = pathToPlay.toString();
        String pathToLogFile = "log.txt";
        String mainDirectory = "library";
        int maxFileSize = 10485760;

        ContentValidator contentValidator = new ContentValidator(maxFileSize);
        Downloader downloader = new Downloader(contentValidator, mainDirectory, pathToLogFile);

        downloader.download(pathToPlaylist);

        deleteDirectories(mainDirectory);
    }
}
