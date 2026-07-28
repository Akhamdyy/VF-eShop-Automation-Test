package com.vf.test.pageobjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class RedLineCheckoutPage extends BasePage {

    private static final By WHO_ARE_YOU_BUYING_FOR_DROPDOWN = By.id("sl-checkout-main-form-div-2");
    private static final By NATIONAL_ID_FIELD = By.id("nationalId");
    private static final By FIRST_NAME_FIELD = By.id("firstName");
    private static final By LAST_NAME_FIELD = By.id("lastName");
    private static final By NEXT_BUTTON = By.xpath("//button[normalize-space()='Next']");
    private static final By FIRST_PAYMENT_DOC_OPTION = By.id("sl-payment-support-docs-input-1");
    private static final By CHECKOUT_BUTTON = By.xpath("//button[normalize-space()='Checkout']");

    public RedLineCheckoutPage(WebDriver driver) {
        super(driver);
    }

    public void selectWhoAreYouBuyingFor(String option) {
        WebElement dropdown = wait.until(ExpectedConditions.elementToBeClickable(WHO_ARE_YOU_BUYING_FOR_DROPDOWN));
        click(dropdown);
        By optionLocator = By.xpath("//*[normalize-space(text())='" + option + "']");
        WebElement optionElement = wait.until(ExpectedConditions.elementToBeClickable(optionLocator));
        click(optionElement);
    }

    public void enterBuyerDetails(String nationalId, String firstName, String lastName) {
        WebElement nationalIdField = wait.until(ExpectedConditions.visibilityOfElementLocated(NATIONAL_ID_FIELD));
        type(nationalIdField, nationalId);
        type(driver.findElement(FIRST_NAME_FIELD), firstName);
        type(driver.findElement(LAST_NAME_FIELD), lastName);
    }

    public void clickNext() {
        click(wait.until(ExpectedConditions.elementToBeClickable(NEXT_BUTTON)));
    }

    public void selectFirstPaymentSupportDocOption() {
        WebElement option = wait.until(ExpectedConditions.presenceOfElementLocated(FIRST_PAYMENT_DOC_OPTION));
        clickHidden(option);
    }

    public void clickCheckout() {
        click(wait.until(ExpectedConditions.elementToBeClickable(CHECKOUT_BUTTON)));
    }
}
