package com.vf.test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class RedLinePlanPage extends BasePage {

    public RedLinePlanPage(WebDriver driver) {
        super(driver);
    }

    public void selectPlan(String planName) {
        By cardsLocator = By.cssSelector("vf-tariff-card");
        wait.until(ExpectedConditions.visibilityOfElementLocated(cardsLocator));
        List<WebElement> cards = driver.findElements(cardsLocator);
        for (WebElement card : cards) {
            WebElement header = card.findElement(By.tagName("h2"));
            if (header.getText().trim().equalsIgnoreCase(planName.trim())) {
                WebElement selectButton = card.findElement(By.xpath(".//button[normalize-space()='Select Plan']"));
                click(selectButton);
                return;
            }
        }
        throw new NoSuchElementException("No RED plan found matching: " + planName);
    }
}
