package com.vf.test.pageobjects;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(30));
        PageFactory.initElements(driver, this);
    }

    protected void click(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block:'center'});", element);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        element.click();
    }

    protected void jsClick(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    protected WebElement findInShadowDom(String cssSelector) {
        return wait.until(d -> {
            Object result = ((JavascriptExecutor) d).executeScript(
                "function search(root, sel) {" +
                "  var el = root.querySelector(sel); if (el) return el;" +
                "  for (var c of root.querySelectorAll('*')) {" +
                "    if (c.shadowRoot) { var f = search(c.shadowRoot, sel); if (f) return f; }" +
                "  } return null;" +
                "} return search(document, arguments[0]);", cssSelector);
            return result instanceof WebElement ? (WebElement) result : null;
        });
    }

    protected void type(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    protected void waitForVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
    }
}
