package com.vf.test.stepdefinitions;

import com.vf.test.pageobjects.*;
import io.cucumber.java.en.*;
import org.testng.Assert;

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

    @Then("the cart should be empty")
    public void the_cart_should_be_empty() {
        Assert.assertEquals(cartPage.getItemCount(), 0, "Cart still contains items after removal");
    }

    @Then("Choose This Line should remain disabled")
    public void choose_this_line_should_remain_disabled() {
        Assert.assertTrue(redLineNumberPage.isChooseThisLineDisabled(),
                "Choose This Line was enabled without a number selected");
    }

    @Then("the {string} plan should not be available")
    public void the_plan_should_not_be_available(String planName) {
        Assert.assertFalse(redLinePlanPage.isPlanAvailable(planName),
                "Unsupported plan '" + planName + "' was unexpectedly available");
    }

    @Then("the Next button on the buyer details form should remain disabled")
    public void the_next_button_should_remain_disabled() {
        Assert.assertTrue(redLineCheckoutPage.isNextDisabled(),
                "Next button was enabled with invalid/incomplete buyer details");
    }

    @Then("the Checkout button should remain disabled")
    public void the_checkout_button_should_remain_disabled() {
        Assert.assertTrue(redLineCheckoutPage.isCheckoutDisabled(),
                "Checkout button was enabled without a payment support option selected");
    }

    @Then("the current page URL should not contain the national id {string}")
    public void the_current_page_url_should_not_contain_the_national_id(String nationalId) {
        Assert.assertFalse(Hooks.driver.getCurrentUrl().contains(nationalId),
                "National ID was exposed in the page URL");
    }
}
