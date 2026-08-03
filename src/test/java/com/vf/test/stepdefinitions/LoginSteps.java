package com.vf.test.stepdefinitions;

import com.vf.test.pageobjects.HomePage;
import com.vf.test.pageobjects.LoginPage;
import io.cucumber.java.en.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class LoginSteps {

    private HomePage homePage;
    private LoginPage loginPage;

    @Given("the user opens the eShop website")
    public void the_user_opens_the_eshop_website() {
        String url = Hooks.config.getProperty("base.url");
        homePage = new HomePage(Hooks.driver);
        homePage.open(url);
    }

    @When("the user sets the site language to {string}")
    public void the_user_sets_the_site_language_to(String language) {
        homePage.setLanguage(language);
    }

    @Then("the login icon should be on the {string} side of the page")
    public void the_login_icon_should_be_on_the_side_of_the_page(String expectedSide) {
        Assert.assertEquals(homePage.getLoginIconSide(), expectedSide.toLowerCase(),
                "Login icon is not on the expected side of the page");
    }

    @When("the user clicks the login icon")
    public void the_user_clicks_the_login_icon() {
        homePage.clickLoginIcon();
        loginPage = new LoginPage(Hooks.driver);
    }

    @When("the user logs in with username {string} and password {string}")
    public void the_user_logs_in_with_username_and_password(String username, String password) {
        loginPage.login(Hooks.resolve(username), Hooks.resolve(password));
    }

    @Then("the user should be redirected back to the eShop homepage")
    public void the_user_should_be_redirected_back_to_the_eshop_homepage() {
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.urlContains("eshop.vodafone.com.eg"));
        Assert.assertTrue(Hooks.driver.getCurrentUrl().contains("eshop.vodafone.com.eg"),
                "User was not redirected back to the eShop homepage after login");
    }

    @Then("the login should be rejected and no session should be created")
    public void the_login_should_be_rejected_and_no_session_should_be_created() {
        loginPage.assertInvalidCredentialsErrorShown();
        Assert.assertFalse(Hooks.driver.getCurrentUrl().contains("eshop.vodafone.com.eg"),
                "User appears to have been logged in despite invalid credentials");
    }
}
