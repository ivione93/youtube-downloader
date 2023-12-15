package org.ivione93;

import org.jboss.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConverterResultPage extends ConverterPage {

    private static final String INVALID_VIDEO = "Invalid Youtube video url. Please try again.";

    static By invalidYoutubeVideoById = By.id("result_container");
    @FindBy(how = How.ID, id = "result_container")
    WebElement invalidYoutubeVideo;

    static By convertButtonById = By.id("btn-convert");
    @FindBy(how = How.ID, using = "btn-convert")
    WebElement convertButton;

    public ConverterResultPage(WebDriver driver) {
        super(driver);
    }

    protected ConverterDownloadPage convertVideo() {
        ConverterDownloadPage downloadPage = null;

        wait.until(ExpectedConditions.and(ExpectedConditions.presenceOfElementLocated(invalidYoutubeVideoById),
                ExpectedConditions.visibilityOfElementLocated(invalidYoutubeVideoById)));
        if (INVALID_VIDEO.equals(invalidYoutubeVideo.getText())) {
            Logger.getLogger(ConverterResultPage.class.getName()).log(Logger.Level.WARN, "Invalid Youtube video url");
        } else {
            Logger.getLogger(ConverterResultPage.class.getName()).log(Logger.Level.INFO, "Valid Youtube video url");
            try {
                Logger.getLogger(ConverterFindPage.class.getName()).log(Logger.Level.INFO, "============ CONVERTING");
                wait.until(ExpectedConditions.and(
                        ExpectedConditions.presenceOfElementLocated(convertButtonById),
                        ExpectedConditions.visibilityOfElementLocated(convertButtonById),
                        ExpectedConditions.elementToBeClickable(convertButtonById)
                ));
                convertButton.click();
                //Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "(Converting) Wait 7 seconds");
                //Thread.sleep(7000);

                waitABit.accept(driver);

                downloadPage = PageFactory.initElements(driver, ConverterDownloadPage.class);
            } catch (Exception ex) {
                Logger.getLogger(ConverterResultPage.class.getName()).log(Logger.Level.ERROR, "Error converting video...");
            }
        }
        
        return downloadPage;
    }
}
