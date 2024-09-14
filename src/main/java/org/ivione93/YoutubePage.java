package org.ivione93;

import org.jboss.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

public class YoutubePage extends ConverterPage {

    private static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_LOCATION = "src/resources/chromedriver.exe";

    private static final String DOWNLOAD_BASE = System.getProperty("user.home");
    private static final String DOWNLOAD_LOCATION_SUFFIX = File.separator + "Downloads";
    private static final String DOWNLOAD_LOCATION = DOWNLOAD_BASE + DOWNLOAD_LOCATION_SUFFIX;
    private static final String DOWNLOAD_ALBUM_DIRECTORY = DOWNLOAD_LOCATION + File.separator;

    private String albumTitle = "";
    private static final String PREFIX = "tomp3.cc - ";

    public YoutubePage(WebDriver driver) {
        super(driver);
    }

    protected List<String> getUrl(String url) {
        System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_LOCATION);
        driver.get(url);

        Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "============ GET SONG URLS");
        List<String> songUrls = new ArrayList<>();
        try {
            Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "(Init Youtube) Wait 5 seconds");
            Thread.sleep(5000);
            this.albumTitle = driver.findElement(By.xpath("//*[@id=\"header-description\"]/h3[1]/yt-formatted-string/a")).getText();
            Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "Title album: " + albumTitle);
            createFolder();

            List<WebElement> songLinks = driver.findElements(By.xpath("//a[@id='wc-endpoint']"));
            for (WebElement songLink : songLinks) {
                String songUrl = songLink.getAttribute("href");
                songUrls.add(songUrl);
            }
        } catch (Exception ex) {
            Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.ERROR, "Song url not found");
        }
        return songUrls;
    }

    private void createFolder() {
        File albumDirectory = new File(DOWNLOAD_ALBUM_DIRECTORY + this.albumTitle);
        if (!albumDirectory.exists()) {
            albumDirectory.mkdirs();
        }
    }

    void moveFiles() {
        Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "============ MOVE FILES");

        String sourceFolder = DOWNLOAD_LOCATION;
        String destinationFolder = sourceFolder + File.separator + this.albumTitle;

        try (Stream<Path> files = Files.list(Paths.get(sourceFolder))) {
            files.filter(file -> file.toString().toLowerCase().endsWith(".mp3"))
                    .forEach(file -> {
                        try {
                            Path destinationPath = Paths.get(destinationFolder, file.getFileName().toString());
                            // Remove "tomp3.cc - "
                            if (file.toFile().getName().startsWith(PREFIX)) {
                                String newName = file.toFile().getName().substring(PREFIX.length());
                                destinationPath = Paths.get(destinationFolder, newName);
                            }
                            Files.move(file, destinationPath);
                        } catch (IOException e) {
                            Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.ERROR, "Error moving file...");
                        }
                    });
        } catch (IOException e) {
            Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.ERROR, "Error getting files...");
        }
        Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "************************");
        Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "*** ENJOY THE ALBUM! ***");
        Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "************************");
    }
}
