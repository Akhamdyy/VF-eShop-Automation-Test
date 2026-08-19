package com.vf.test.stepdefinitions;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.URI;
import java.time.Duration;

/**
 * Step defs for triage_tool_verification.feature - deliberately fail in distinct, controlled
 * ways to exercise every classification category of the AI triage tool. Kept isolated from
 * real page objects/step defs so this can never mask or interfere with actual product test
 * results.
 */
public class TriageVerificationSteps {

    @Given("the user opens the eShop website cleanly")
    public void the_user_opens_the_eshop_website_cleanly() {
        Hooks.driver.get(Hooks.config.getProperty("base.url"));
        dismissCommonOverlays();
    }

    @Given("the user opens the eShop website without dismissing overlays")
    public void the_user_opens_the_eshop_website_without_dismissing_overlays() {
        Hooks.driver.get(Hooks.config.getProperty("base.url"));
    }

    @Given("the user navigates to a non-existent page path")
    public void the_user_navigates_to_a_non_existent_page_path() {
        URI base = URI.create(Hooks.config.getProperty("base.url"));
        String origin = base.getScheme() + "://" + base.getHost();
        Hooks.driver.get(origin + "/en/triage-tool-verification-page-does-not-exist");
    }

    @Then("a nonexistent element locator should fail to be found")
    public void a_nonexistent_element_locator_should_fail_to_be_found() {
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(15))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.id("triage-tool-verification-locator-does-not-exist")));
    }

    @Then("clicking an element hidden behind the overlay should fail")
    public void clicking_an_element_hidden_behind_the_overlay_should_fail() {
        // Regular (non-JS) click, so a real overlay in front of the element throws
        // ElementClickInterceptedException instead of silently succeeding.
        Hooks.driver.findElement(By.id("sl-user-profile-button-1")).click();
    }

    @Then("an element that only exists on a real page should be visible")
    public void an_element_that_only_exists_on_a_real_page_should_be_visible() {
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(
                        By.xpath("//*[starts-with(@id, \"sl-category-section-vf-category-card-1-\")]")));
    }

    @Then("the page title should incorrectly equal {string}")
    public void the_page_title_should_incorrectly_equal(String expected) {
        Assert.assertEquals(Hooks.driver.getTitle(), expected,
                "Deliberate mismatch for triage tool verification");
    }

    @Then("a deliberately ambiguous failure occurs with no page context")
    public void a_deliberately_ambiguous_failure_occurs_with_no_page_context() {
        throw new RuntimeException("Unexpected internal state during triage tool verification");
    }

    private void dismissCommonOverlays() {
        try {
            Hooks.driver.findElement(By.id("onetrust-accept-btn-handler")).click();
        } catch (WebDriverException ignored) {
        }
        try {
            Hooks.driver.findElement(By.cssSelector("button.close-modal-desktop")).click();
        } catch (WebDriverException ignored) {
        }
    }
}
