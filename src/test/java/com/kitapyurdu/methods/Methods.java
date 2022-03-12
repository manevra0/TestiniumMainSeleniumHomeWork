package com.kitapyurdu.methods;

import com.kitapyurdu.driver.BaseTestForKitapYurdu;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Select;

import java.time.Duration;
import java.util.List;
import java.util.Random;


public class Methods {
    WebDriver driver;
    FluentWait<WebDriver> wait;
    JavascriptExecutor jsdriver;
    Logger logger = LogManager.getLogger(Methods.class);
    private By by;

    public Methods() {

        driver = BaseTestForKitapYurdu.driver;
        wait = new FluentWait<WebDriver>(driver);
        wait.withTimeout(Duration.ofSeconds(30))
                .pollingEvery(Duration.ofMillis(300))
                .ignoring(NoSuchElementException.class);
        jsdriver = (JavascriptExecutor) driver;
    }

    public WebElement findElement(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public void click(By by) {
        findElement(by).click();
    }

    public void waitBySeconds(long seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public boolean isElementVisible(By by) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(by));
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public void addFavoritesControl() {
        click(By.cssSelector(".menu.top.my-list"));
        click(By.linkText("Favorilerim"));
        Assert.assertTrue(isElementVisible(By.cssSelector(".limit")));
        logger.info("Favorilere eklenmiştir");

    }


    public void sendKeys(By by, String text) {
        findElement(by).sendKeys(text);
    }

    public void scrollWithAction(By by) {
        Actions actions = new Actions(driver);
        actions.moveToElement(findElement(by)).build().perform();
    }


    public List<WebElement> findElements(By by) {
        wait.until(ExpectedConditions.presenceOfAllElementsLocatedBy(by));
        return driver.findElements(by);
    }


    public void addFavorites() {
        List<WebElement> products = findElements(By.cssSelector(".add-to-favorites"));
        for (int i = 5; i < 9; i++) {
            WebElement element = products.get(i);
            scrollWithAction(element);
            products.get(i).click();
            waitBySeconds(1);

        }
    }

    public void scrollWithAction(WebElement element) {
    }

    public void scrollWithJavaScript(By by) {
        jsdriver.executeScript("arguments[0].scrollIntoView();", findElement(by));
    }

    public Select getSelect(By by) {
        return new Select(findElement(by));
    }

    public void selectByText(By by, String text) {
        getSelect(by).selectByVisibleText(text);
    }

    public String getAttribute(By by, String attributeName) {
        return findElement(by).getAttribute(attributeName);
    }

    public String getText(By by) {
        return findElement(by).getText();
    }

    public String getValue(By by) {
        return jsdriver.executeScript("return arguments[0].value", findElement(by)).toString();
    }




}