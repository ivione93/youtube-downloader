package org.ivione93;

import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.NoSuchElementException;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConverterPage {

    protected WebDriver driver;
    protected Wait<WebDriver> wait;

    public ConverterPage(WebDriver driver) {
        this.driver = driver;
        wait = waitGenerator.apply(driver);
    }

    protected Function<WebDriver, Wait<WebDriver>> waitGenerator = (WebDriver aDriver) -> new FluentWait<>(aDriver)
            .withTimeout(Duration.ofSeconds(10))
            .pollingEvery(Duration.of(10, ChronoUnit.MILLIS))
            .ignoring(NoSuchElementException.class, TimeoutException.class);

    protected Consumer<WebDriver> waitABit = (WebDriver aDriver) -> {
        try {
            (new WebDriverWait(aDriver, Duration.ofSeconds(1)))
                    .ignoring(NoSuchElementException.class, TimeoutException.class)
                    .until((WebDriver $) -> false);
        } catch (Throwable ex) {
            Logger.getLogger(ConverterPage.class.getName()).log(Level.FINEST, null, ex);
        }
    };
}
