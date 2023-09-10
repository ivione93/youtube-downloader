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

    static By youtubeLinkValueById = By.id("k__input");
    @FindBy(how = How.ID, using = "k__input")
    WebElement youtubeLinkValue;

    static By startButtonById = By.id("btn-start");
    @FindBy(how = How.ID, using = "btn-start")
    WebElement startButton;

    public ConverterFindPage(WebDriver driver) {
        super(driver);
    }

    public ConverterResultPage findLink(String url)  {
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
                ExpectedConditions.presenceOfElementLocated(startButtonById),
                ExpectedConditions.visibilityOfElementLocated(startButtonById),
                ExpectedConditions.elementToBeClickable(startButtonById)
        ));
        startButton.click();

        waitABit.accept(driver);

        return PageFactory.initElements(driver, ConverterResultPage.class);
    }
}
