package com.vf.test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RedLineNumberPage extends BasePage {

    public RedLineNumberPage(WebDriver driver) {
        super(driver);
    }

    public void selectSimType(String simType) {
        String value = simType.equalsIgnoreCase("eSim") ? "esim" : "physical";
        By locator = By.cssSelector("label.custom-radio:has(input[name='redLinesSimType'][value='" + value + "'])");
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(locator));
        click(option);
    }

    public void selectAnyNumber() {
        By locator = By.cssSelector("div.msisdn-item");
        wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        List<WebElement> numbers = driver.findElements(locator);
        click(numbers.get(0));
    }

    public void clickChooseThisLine() {
        By locator = By.xpath("//button[normalize-space()='Choose this line']");
        WebElement chooseButton = wait.until(ExpectedConditions.elementToBeClickable(locator));
        click(chooseButton);
    }

    public boolean isChooseThisLineDisabled() {
        By locator = By.xpath("//button[normalize-space()='Choose this line']");
        WebElement chooseButton = wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
        return !chooseButton.isEnabled();
    }
}
