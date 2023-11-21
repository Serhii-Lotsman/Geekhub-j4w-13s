package org.geekhub.hw5;

import org.geekhub.hw5.exception.ContentLengthNotKnownException;
import org.geekhub.hw5.exception.FileException;
import org.geekhub.hw5.exception.FileExistException;
import org.geekhub.hw5.exception.LimitSizeException;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static org.geekhub.hw5.FileUtils.createDirectories;
import static org.geekhub.hw5.FileUtils.readAllLines;

public class Downloader {

    public static final String FILE_EXTENSION = ".mp3";
    public static final String PLAYLIST_DELIMITER = Pattern.quote("|");
    public static final String FILE_DIRECTORY_DELIMITER = "/";
    public static final int MAX_DIRECTORIES_SIZE = 3;

    private final ContentValidator contentValidator;
    private final String mainDirectory;
    private final Path pathToLogFile;

    public Downloader(ContentValidator contentValidator, String mainDirectory, String pathToLogFile) {
        this.contentValidator = contentValidator;
        this.mainDirectory = mainDirectory;
        this.pathToLogFile = Path.of(pathToLogFile);
    }

    public void download(String pathToPlaylist) {
        List<String> linesFromPlaylist = readAllLines(pathToPlaylist);

        linesFromPlaylist.forEach(lineFromPlaylist -> {
            String[] split = lineFromPlaylist.split(PLAYLIST_DELIMITER);
            URL url = getUrl(split[split.length - 1]);
            String filename = String.format("%s%s", split[split.length - 2].strip(), FILE_EXTENSION);
            Path pathToFile = Path.of(mainDirectory, getDirectoriesToFile(split));

            if (isValid(url, pathToFile, filename)) {
                createDirectories(pathToFile);
                if (url != null) {
                    saveToFile(url, pathToFile, filename);
                }
            }
        });
    }

    private void saveToFile(URL url, Path pathToFile, String filename) {
        try (InputStream inputStream = url.openStream()) {
            Path path = Path.of(pathToFile.toString(), filename);
            Files.copy(inputStream, path);
        } catch (IOException e) {
            String message = String.format("Download error: Unable to download file from link %s%n", url);
            handleException(message);
        }
    }

    private String getDirectoriesToFile(String[] split) {
        return Arrays.stream(split)
            .map(String::strip)
            .limit(MAX_DIRECTORIES_SIZE)
            .collect(Collectors.joining(FILE_DIRECTORY_DELIMITER));
    }

    private URL getUrl(String url) {
        try {
            return new URL(url);
        } catch (MalformedURLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private boolean isValid(URL url, Path pathToFile, String filename) {
        try {
            return contentValidator.isValid(url, pathToFile.toString(), filename);
        } catch (IOException | LimitSizeException | ContentLengthNotKnownException e) {
            String message = e.getMessage();
            handleException(message);
            return false;
        } catch (FileExistException e) {
            String message = e.getMessage();
            handleException(message);
            return true;
        }

    }

    private void handleException(String message) {
        try {
            Files.writeString(pathToLogFile, message);
        } catch (FileException | IOException exception) {
            throw new FileException("Failed to write to log-file", exception);
        }
    }
}
