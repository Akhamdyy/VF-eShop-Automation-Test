package com.vf.test.runners;

import io.cucumber.testng.AbstractTestNGCucumberTests;
import io.cucumber.testng.CucumberOptions;

@CucumberOptions(
        features = "src/test/resources/features",
        glue = "com.vf.test.stepdefinitions",
        plugin = {
                "pretty",
                "html:target/cucumber-reports/report.html",
                "io.qameta.allure.cucumber7jvm.AllureCucumber7Jvm",
                "com.aitriage.sdk.TriageEventListener"
        },
        // Excludes the triage-tool verification suite (deliberately-failing scenarios used to
        // exercise the AI triage tool itself) from normal runs. Run it explicitly with:
        // mvn test "-Dcucumber.filter.tags=@triageToolVerification"
        tags = "not @triageToolVerification",
        monochrome = true
)
public class TestRunner extends AbstractTestNGCucumberTests {
}
