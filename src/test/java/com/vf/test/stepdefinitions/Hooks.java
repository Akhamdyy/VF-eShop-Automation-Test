package com.vf.test.stepdefinitions;

import com.aitriage.sdk.TriageSdk;
import io.cucumber.java.After;
import io.cucumber.java.AfterStep;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.Duration;
import java.util.List;
import java.util.Properties;

public class Hooks {

    public static WebDriver driver;
    public static Properties config = new Properties();
    private static TriageSdk triageSdk;

    @Before
    public void setUp() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        config.load(input);
        loadEnvFile();

        triageSdk = new TriageSdk(
                config.getProperty("triage.service.url"),
                config.getProperty("triage.project.id"));

        System.setProperty("webdriver.edge.driver",
                System.getProperty("user.home") + "\\AppData\\Local\\Microsoft\\WinGet\\Packages\\Microsoft.EdgeDriver_Microsoft.Winget.Source_8wekyb3d8bbwe\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @AfterStep
    public void embedFailureArtifacts(Scenario scenario) {
        triageSdk.onStepFailure(scenario, driver);
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }

    private void loadEnvFile() throws IOException {
        Path envPath = Path.of(System.getProperty("user.dir"), ".env");
        if (!Files.exists(envPath)) {
            return;
        }
        List<String> lines = Files.readAllLines(envPath);
        for (String line : lines) {
            String trimmed = line.trim();
            if (trimmed.isEmpty() || trimmed.startsWith("#") || !trimmed.contains("=")) {
                continue;
            }
            int separatorIndex = trimmed.indexOf('=');
            String key = trimmed.substring(0, separatorIndex).trim();
            String value = trimmed.substring(separatorIndex + 1).trim();
            config.setProperty(key, value);
        }
    }

    /**
     * Resolves "{env:KEY}" placeholders used in feature files against values loaded from .env,
     * so test credentials and PII never appear as literals in Gherkin.
     */
    public static String resolve(String value) {
        if (value != null && value.startsWith("{env:") && value.endsWith("}")) {
            String key = value.substring(5, value.length() - 1);
            String resolved = config.getProperty(key);
            if (resolved == null) {
                throw new IllegalStateException("Missing .env value for key: " + key);
            }
            return resolved;
        }
        return value;
    }
}
