package org.ivione93;

import org.jboss.logging.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class ConverterFindPage extends ConverterPage {

    static By youtubeLinkValueById = By.id("url");
    @FindBy(how = How.ID, using = "url")
    WebElement youtubeLinkValue;

    static By convertButtonById = By.xpath("/html/body/form/div[2]/input[2]");
    @FindBy(how = How.XPATH, using = "/html/body/form/div[2]/input[2]")
    WebElement convertButton;

    public ConverterFindPage(WebDriver driver) {
        super(driver);
    }

    public DownloadPage findLink(String url) {
        Logger.getLogger(ConverterFindPage.class.getName()).log(Logger.Level.INFO, "=================");
        Logger.getLogger(ConverterFindPage.class.getName()).log(Logger.Level.INFO, "============ FIND");
        Logger.getLogger(ConverterFindPage.class.getName()).log(Logger.Level.INFO, "Searching for: {0}", new String[]{url});

        wait.until(ExpectedConditions.and(
           ExpectedConditions.presenceOfElementLocated(youtubeLinkValueById),
           ExpectedConditions.visibilityOfElementLocated(youtubeLinkValueById),
           ExpectedConditions.elementToBeClickable(youtubeLinkValueById)
        ));

        youtubeLinkValue.clear();
        youtubeLinkValue.sendKeys(url);

        wait.until(ExpectedConditions.and(
                ExpectedConditions.presenceOfElementLocated(convertButtonById),
                ExpectedConditions.visibilityOfElementLocated(convertButtonById),
                ExpectedConditions.elementToBeClickable(convertButtonById)
        ));
        convertButton.click();

        waitABit.accept(driver);

        return PageFactory.initElements(driver, DownloadPage.class);
    }
}
