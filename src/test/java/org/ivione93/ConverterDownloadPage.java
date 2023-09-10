package org.ivione93;

import org.jboss.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;

public class ConverterDownloadPage extends ConverterPage {

    static By downloadButtonById = By.id("asuccess");
    @FindBy(how = How.ID, using = "asuccess")
    WebElement downloadButton;

    public ConverterDownloadPage(WebDriver driver) {
        super(driver);
    }

    protected void downloadVideo() {
        Logger.getLogger(ConverterDownloadPage.class.getName()).log(Logger.Level.INFO, "============ DOWNLOADING");
        if (downloadButtonById != null) {
            Logger.getLogger(ConverterDownloadPage.class.getName()).log(Logger.Level.WARN, "Start download");
            try {
                wait.until(ExpectedConditions.and(
                        ExpectedConditions.presenceOfElementLocated(downloadButtonById),
                        ExpectedConditions.visibilityOfElementLocated(downloadButtonById),
                        ExpectedConditions.elementToBeClickable(downloadButtonById)
                ));
                downloadButton.click();
                Logger.getLogger(YoutubePage.class.getName()).log(Logger.Level.INFO, "(Downloading) Wait 5 seconds");
                Thread.sleep(5000);

                // Cambiar el enfoque a la segunda pestaña
                ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
                if (tabs.size() > 1) {
                    System.out.println("Entra: " + tabs.size());
                    driver.switchTo().window(tabs.get(1));
                    driver.close();

                    driver.switchTo().window(tabs.get(0));
                    System.out.println("Sale: " + tabs.size());
                }

                Logger.getLogger(ConverterDownloadPage.class.getName()).log(Logger.Level.INFO, "Video downloaded!");
                waitABit.accept(driver);
            } catch (Exception ex) {
                Logger.getLogger(ConverterDownloadPage.class.getName()).log(Logger.Level.WARN, "Error downloading video");
                if (!driver.getCurrentUrl().contains("tomp3")) {
                    driver.navigate().back();
                }
            }
        } else {
            Logger.getLogger(ConverterDownloadPage.class.getName()).log(Logger.Level.ERROR, "Download button not found");
        }
    }
}
