package com.vf.test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.List;

public class CartPage extends BasePage {

    private static final By REMOVE_BUTTON = By.cssSelector("div.cart-actions p");

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public void waitForCartPage() {
        wait.until(ExpectedConditions.urlContains("/cart"));
    }

    public void removeAllItems() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(REMOVE_BUTTON));
        List<WebElement> removeButtons = driver.findElements(REMOVE_BUTTON);
        while (!removeButtons.isEmpty()) {
            int countBeforeRemoval = removeButtons.size();
            click(removeButtons.get(0));
            wait.until(d -> d.findElements(REMOVE_BUTTON).size() < countBeforeRemoval);
            removeButtons = driver.findElements(REMOVE_BUTTON);
        }
    }
}
