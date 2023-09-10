package org.ivione93;

import org.jboss.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class YoutubePage extends ConverterPage {

    private static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_LOCATION = "src/resources/chromedriver.exe";

    private static final String DOWNLOAD_BASE = System.getProperty("user.home");
    private static final String DOWNLOAD_LOCATION_SUFFIX = File.separator + "Downloads";
    private static final String DOWNLOAD_LOCATION = DOWNLOAD_BASE + DOWNLOAD_LOCATION_SUFFIX;
    private static final String DOWNLOAD_ALBUM_DIRECTORY = DOWNLOAD_LOCATION + File.separator;

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
            String albumTitle = driver.findElement(By.xpath("//*[@id=\"header-description\"]/h3[1]/yt-formatted-string/a")).getText();
            System.out.println("Title album: " + albumTitle);
            createDirectories(albumTitle);

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

    private static void createDirectories(String albumTitle) {
        File albumDirectory = new File(DOWNLOAD_ALBUM_DIRECTORY + albumTitle);
        if (!albumDirectory.exists()) {
            albumDirectory.mkdirs();
        }
    }
}
