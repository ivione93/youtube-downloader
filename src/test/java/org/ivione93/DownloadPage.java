package org.ivione93;

import org.jboss.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;

public class DownloadPage extends ConverterPage {

    static By downloadButtonById = By.xpath("/html/body/form/div[2]/a[1]");
    @FindBy(how = How.XPATH, using = "/html/body/form/div[2]/a[1]")
    WebElement downloadButton;

    static By convertNextButtonById = By.xpath("/html/body/form/div[2]/a[2]");
    @FindBy(how = How.XPATH, using = "/html/body/form/div[2]/a[2]")
    WebElement convertNextButton;

    public DownloadPage(WebDriver driver) {
        super(driver);
    }

    protected void downloadSong() {
        Logger.getLogger(DownloadPage.class.getName()).log(Logger.Level.INFO, "============ DOWNLOADING");
        if (downloadButtonById != null) {
            Logger.getLogger(DownloadPage.class.getName()).log(Logger.Level.WARN, "Start download");
            try {
                wait.until(ExpectedConditions.and(
                        ExpectedConditions.presenceOfElementLocated(downloadButtonById),
                        ExpectedConditions.visibilityOfElementLocated(downloadButtonById),
                        ExpectedConditions.elementToBeClickable(downloadButtonById)
                ));
                downloadButton.click();

                Logger.getLogger(DownloadPage.class.getName()).log(Logger.Level.INFO, "Downloaded!");

                // Se cambia el enfoque a la segunda pestaña y se cierra
                ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
                if (tabs.size() > 1) {
                    driver.switchTo().window(tabs.get(1));
                    driver.close();

                    driver.switchTo().window(tabs.get(0));
                }

                // Convert next
                if (convertNextButtonById != null) {
                    try {
                        wait.until(ExpectedConditions.and(
                                ExpectedConditions.presenceOfElementLocated(convertNextButtonById),
                                ExpectedConditions.visibilityOfElementLocated(convertNextButtonById),
                                ExpectedConditions.elementToBeClickable(convertNextButtonById)
                        ));
                        convertNextButton.click();
                    } catch (Exception ex) {
                        Logger.getLogger(DownloadPage.class.getName()).log(Logger.Level.WARN, "Error in convert next song...");
                    }
                }
            } catch (Exception ex) {
                Logger.getLogger(DownloadPage.class.getName()).log(Logger.Level.WARN, "Error downloading...");
            }
            waitABit.accept(driver);
        } else {
            Logger.getLogger(DownloadPage.class.getName()).log(Logger.Level.ERROR, "Error converting. Download button not found...");
        }
    }
}
