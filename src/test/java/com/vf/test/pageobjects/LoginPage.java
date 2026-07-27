package com.vf.test.pageobjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BasePage {

    @FindBy(xpath = "//*[@id=\"username\"]")
    private WebElement usernameField;

    @FindBy(xpath = "//*[@id=\"password\"]")
    private WebElement passwordField;

    @FindBy(xpath = "//*[@id=\"submitBtn\"]")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        jsClick(loginButton);
    }
}
