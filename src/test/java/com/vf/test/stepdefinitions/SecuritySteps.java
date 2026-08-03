package com.vf.test.stepdefinitions;

import io.cucumber.java.en.*;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.util.List;
import java.util.Set;

public class SecuritySteps {

    private Set<Cookie> cookiesBeforeLogin;

    @When("the user directly navigates to the {string} page without logging in")
    public void the_user_directly_navigates_to_the_page_without_logging_in(String path) {
        URI base = URI.create(Hooks.config.getProperty("base.url"));
        String origin = base.getScheme() + "://" + base.getHost();
        Hooks.driver.get(origin + path);
    }

    @Then("the user should be redirected to the login page instead of the cart")
    public void the_user_should_be_redirected_to_the_login_page_instead_of_the_cart() {
        new WebDriverWait(Hooks.driver, Duration.ofSeconds(20))
                .until(d -> d.getCurrentUrl().contains("/auth/realms/"));
        Assert.assertTrue(Hooks.driver.getCurrentUrl().contains("/auth/realms/"),
                "Unauthenticated user was not redirected to the login page when accessing the cart");
    }

    @When("the user records the current session cookies")
    public void the_user_records_the_current_session_cookies() {
        cookiesBeforeLogin = Hooks.driver.manage().getCookies();
    }

    @Then("the session cookies should have changed since login began")
    public void the_session_cookies_should_have_changed_since_login_began() {
        Set<Cookie> cookiesAfterLogin = Hooks.driver.manage().getCookies();
        Assert.assertNotEquals(cookiesAfterLogin, cookiesBeforeLogin,
                "Session cookies did not change after authentication");
    }

    @Then("the eShop site should enforce HTTPS with security headers")
    public void the_eshop_site_should_enforce_https_with_security_headers() throws Exception {
        String url = Hooks.config.getProperty("base.url");
        Assert.assertTrue(url.startsWith("https://"), "Base URL is not served over HTTPS");

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder(URI.create(url)).GET().build();
        HttpResponse<Void> response = client.send(request, HttpResponse.BodyHandlers.discarding());

        Assert.assertTrue(response.uri().toString().startsWith("https://"),
                "Response was not served over HTTPS");
        Assert.assertTrue(response.headers().firstValue("content-security-policy").isPresent(),
                "Content-Security-Policy header is missing");
    }

    @Then("all externally loaded scripts on the homepage should use secure origins")
    public void all_externally_loaded_scripts_should_use_secure_origins() {
        @SuppressWarnings("unchecked")
        List<String> scriptSrcs = (List<String>) ((JavascriptExecutor) Hooks.driver).executeScript(
                "return Array.from(document.scripts).map(s => s.src).filter(Boolean);");
        for (String src : scriptSrcs) {
            Assert.assertTrue(src.startsWith("https://") || src.startsWith("blob:"),
                    "Insecure script origin detected: " + src);
        }
    }
}
