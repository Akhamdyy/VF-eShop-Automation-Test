package com.vf.test.stepdefinitions;

import com.vf.test.pageobjects.*;
import io.cucumber.java.en.*;

public class RedLinePurchaseSteps {

    private HomePage homePage;
    private LoginPage loginPage;
    private RedLineNumberPage redLineNumberPage;
    private RedLinePlanPage redLinePlanPage;
    private RedLineCheckoutPage redLineCheckoutPage;
    private CartPage cartPage;

    @Given("the user opens the eShop website and logs in with username {string} and password {string}")
    public void the_user_opens_the_eshop_website_and_logs_in(String username, String password) {
        String url = Hooks.config.getProperty("base.url");
        homePage = new HomePage(Hooks.driver);
        homePage.open(url);
        homePage.clickLoginIcon();
        loginPage = new LoginPage(Hooks.driver);
        loginPage.login(username, password);
    }

    @When("the user clicks on {string} from Shop by Category")
    public void the_user_clicks_on_category_from_shop_by_category(String categoryName) {
        homePage = new HomePage(Hooks.driver);
        homePage.clickCategory(categoryName);
        redLineNumberPage = new RedLineNumberPage(Hooks.driver);
    }

    @When("the user selects {string} for the RED line")
    public void the_user_selects_sim_type_for_the_red_line(String simType) {
        redLineNumberPage.selectSimType(simType);
    }

    @When("the user selects any available RED line number")
    public void the_user_selects_any_available_red_line_number() {
        redLineNumberPage.selectAnyNumber();
    }

    @When("the user clicks Choose This Line")
    public void the_user_clicks_choose_this_line() {
        redLineNumberPage.clickChooseThisLine();
        redLinePlanPage = new RedLinePlanPage(Hooks.driver);
    }

    @When("the user selects the {string} plan")
    public void the_user_selects_the_plan(String planName) {
        redLinePlanPage.selectPlan(planName);
        redLineCheckoutPage = new RedLineCheckoutPage(Hooks.driver);
    }

    @When("the user selects {string} from the who are you buying for dropdown")
    public void the_user_selects_from_the_who_are_you_buying_for_dropdown(String option) {
        redLineCheckoutPage.selectWhoAreYouBuyingFor(option);
    }

    @When("the user enters national id {string} first name {string} and last name {string}")
    public void the_user_enters_national_id_first_name_and_last_name(String nationalId, String firstName, String lastName) {
        redLineCheckoutPage.enterBuyerDetails(nationalId, firstName, lastName);
    }

    @When("the user clicks Next on the buyer details form")
    public void the_user_clicks_next_on_the_buyer_details_form() {
        redLineCheckoutPage.clickNext();
    }

    @When("the user checks the first payment support document option")
    public void the_user_checks_the_first_payment_support_document_option() {
        redLineCheckoutPage.selectFirstPaymentSupportDocOption();
    }

    @When("the user clicks Checkout on the RED line order")
    public void the_user_clicks_checkout_on_the_red_line_order() {
        redLineCheckoutPage.clickCheckout();
        cartPage = new CartPage(Hooks.driver);
    }

    @Then("the user removes the items from the cart")
    public void the_user_removes_the_items_from_the_cart() {
        cartPage.waitForCartPage();
        cartPage.removeAllItems();
    }
}
