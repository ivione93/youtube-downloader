package org.ivione93;

import io.quarkus.test.junit.QuarkusTest;
import org.jboss.logging.Logger;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.support.PageFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@QuarkusTest
public class YoutubeDownloaderTest {

    // ENLACE DEL ALBUM DE YOUTUBE
    private static final String YOUTUBE_URL = "https://www.youtube.com/watch?v=7tKc8zqYuNY&list=OLAK5uy_mrutO_7pM_CaHKhI54Bq-19_PYZEko_HE";

    private static final String CHROME_DRIVER_KEY = "webdriver.chrome.driver";
    private static final String CHROME_DRIVER_LOCATION = "src/resources/chromedriver.exe";
    private static final String BRAVE_LOCATION = "C:\\Program Files\\BraveSoftware\\Brave-Browser\\Application\\brave.exe";
    private static final String CONVERTER_URL = "https://tomp3.cc/est58gv";

    private static final String DOWNLOAD_BASE = System.getProperty("user.home");
    private static final String DOWNLOAD_LOCATION_SUFFIX = File.separator + "Downloads";
    private static final String DOWNLOAD_LOCATION = DOWNLOAD_BASE + DOWNLOAD_LOCATION_SUFFIX;

    @Test
    public void testSelenium() {
        System.setProperty(CHROME_DRIVER_KEY, CHROME_DRIVER_LOCATION);

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("profile.default_content_settings.popups",0);
        prefs.put("download.default_directory", DOWNLOAD_LOCATION);
        prefs.put("download.prompt_from_download", false);

        final ChromeOptions options = new ChromeOptions().setBinary(BRAVE_LOCATION);
        options.setExperimentalOption("prefs", prefs);
        options.addArguments(
                "--start-maximized",
                "--lang=en-us",
                "--disable-gpu",
                "--disable-extensions",
                "--disable-popup-blocking",
                "--window-size=1920,1200",
                "--ignore-certificate-errors"
        );
        options.addArguments("download.default_directory=" + DOWNLOAD_LOCATION);
        options.addArguments("download.prompt_for_download=false");
        options.setCapability(CapabilityType.UNHANDLED_PROMPT_BEHAVIOUR, "accept and notify");

        final WebDriver driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        List<String> songUrls = new ArrayList<>();

        try {
            songUrls = getSongLinks(driver);
        } catch (Exception ex) {
            Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.ERROR, "Something wrong. Try again!");
        }

        driver.manage().window().maximize();
        driver.get(CONVERTER_URL);

        ConverterFindPage findPage = PageFactory.initElements(driver, ConverterFindPage.class);
        for (String songUrl : songUrls) {
            findPage
                    .findLink(songUrl)
                    .convertVideo()
                    .downloadVideo();
        }

        Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "Full album downloaded!");
        driver.quit();
    }

    private List<String> getSongLinks(WebDriver driver) {
        YoutubePage youtubePage = PageFactory.initElements(driver, YoutubePage.class);
        List<String> songUrls = youtubePage.getUrl(YOUTUBE_URL);
        Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "Song links:");
        for (String songUrl : songUrls) {
            System.out.println(songUrl);
        }
        return songUrls;
    }

}