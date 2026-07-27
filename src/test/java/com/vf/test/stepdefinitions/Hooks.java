package com.vf.test.stepdefinitions;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.io.IOException;
import java.io.InputStream;
import java.time.Duration;
import java.util.Properties;

public class Hooks {

    public static WebDriver driver;
    public static Properties config = new Properties();

    @Before
    public void setUp() throws IOException {
        InputStream input = getClass().getClassLoader().getResourceAsStream("config.properties");
        config.load(input);

        System.setProperty("webdriver.edge.driver",
                System.getProperty("user.home") + "\\AppData\\Local\\Microsoft\\WinGet\\Packages\\Microsoft.EdgeDriver_Microsoft.Winget.Source_8wekyb3d8bbwe\\msedgedriver.exe");
        EdgeOptions options = new EdgeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        driver = new EdgeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
    }

    @After
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
