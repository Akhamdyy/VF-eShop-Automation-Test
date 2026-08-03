package com.vf.test.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement usernameField;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"submitBtn\"]")
    private WebElement loginButton;

    @FindBy(id = "js-mobileNumberError")
    private WebElement invalidCredentialsError;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        jsClick(loginButton);
    }

    public void assertInvalidCredentialsErrorShown() {
        wait.until(ExpectedConditions.visibilityOf(invalidCredentialsError));
    }
}
