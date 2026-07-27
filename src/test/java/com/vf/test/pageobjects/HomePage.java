package com.vf.test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class HomePage extends BasePage {

    @FindBy(id = "onetrust-accept-btn-handler")
    private WebElement acceptCookiesButton;

    @FindBy(css = "button.close-modal-desktop")
    private WebElement promoModalCloseButton;

    @FindBy(id = "sl-nav-bar-button-1")
    private WebElement languageToggleButton;

    @FindBy(id = "sl-user-profile-button-1")
    private WebElement loginIcon;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        driver.get(url);
        acceptCookies();
        dismissPromoModal();
    }

    private void acceptCookies() {
        try {
            jsClick(acceptCookiesButton);
            wait.until(ExpectedConditions.invisibilityOfElementLocated(
                    By.cssSelector("div.onetrust-pc-dark-filter")));
        } catch (Exception ignored) {
        }
    }

    private void dismissPromoModal() {
        try {
            WebDriverWait shortWait = new WebDriverWait(driver, Duration.ofSeconds(3));
            shortWait.until(ExpectedConditions.visibilityOf(promoModalCloseButton));
            jsClick(promoModalCloseButton);
        } catch (Exception ignored) {
        }
    }

    public void setLanguage(String language) {
        boolean wantArabic = language.equalsIgnoreCase("Arabic");
        boolean isArabic = driver.getCurrentUrl().contains("/ar/");
        if (isArabic != wantArabic) {
            waitForVisible(languageToggleButton);
            jsClick(languageToggleButton);
            wait.until(ExpectedConditions.urlContains(wantArabic ? "/ar/" : "/en/"));
            dismissPromoModal();
        }
    }

    public String getLoginIconSide() {
        waitForVisible(loginIcon);
        long viewportWidth = (long) ((JavascriptExecutor) driver).executeScript("return window.innerWidth");
        double iconCenterX = loginIcon.getRect().getX() + (loginIcon.getRect().getWidth() / 2.0);
        return iconCenterX < viewportWidth / 2.0 ? "left" : "right";
    }

    public void clickLoginIcon() {
        click(loginIcon);
    }
}
