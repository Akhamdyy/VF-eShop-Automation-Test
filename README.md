# VF-eShop-Test

Cucumber + Selenium + TestNG UI test suite for Vodafone Egypt's e-shop
(`eshop.vodafone.com.eg`) — login, security, and the RED-line purchase flow.

Every failure is automatically diagnosed by a local AI triage service (see
[ai-test-triage-service](../TTS%20Tool/README.md)) via the
[ai-test-triage-sdk-java](../TTS%20Tool%20SDK/README.md) SDK — this was the
first project the SDK was integrated into.

## What's covered

**`login.feature`** — language switching (English/Arabic, and the login
icon's side flips with it), valid login, and invalid credentials being
rejected with no session created.

**`security.feature`** — protected routes (`/en/cart`) redirect unauthenticated
users to login; the session identifier rotates after a successful login;
HTTPS is enforced with baseline security headers present; third-party
resources on the homepage load only from secure origins.

**`red_line_purchase.feature`** — the full RED-line purchase funnel (choose
SIM type → pick a number → choose a plan → fill buyer details → accept
payment support → checkout → remove from cart), plus negative/validation
cases: "Choose This Line" stays disabled without a selected number, an
unsupported plan isn't offered, the buyer-details form's Next button rejects
invalid national IDs (blank, too short, non-numeric, too long), checkout
stays disabled without a payment support selection, and the national ID never
leaks into the page URL.

**`triage_tool_verification.feature`** (tagged `@triageToolVerification`,
**excluded from normal runs**) — five scenarios engineered to fail in each of
the AI triage tool's five classification categories. This is not a product
regression suite; it exists to validate the triage tool itself. Run it
explicitly:

```bash
mvn test "-Dcucumber.filter.tags=@triageToolVerification"
```

## Project layout

```
src/test/java/com/vf/test/
  pageobjects/         BasePage, HomePage, LoginPage, CartPage,
                        RedLineNumberPage, RedLinePlanPage, RedLineCheckoutPage
  stepdefinitions/      Hooks (WebDriver + triage SDK setup + .env resolution),
                        LoginSteps, SecuritySteps, RedLinePurchaseSteps,
                        TriageVerificationSteps
  runners/               TestRunner (TestNG + Cucumber entry point)
src/test/resources/
  features/              login.feature, security.feature,
                         red_line_purchase.feature, triage_tool_verification.feature
  config.properties      base URL, triage service settings
.env.example             template for local secrets (copy to .env, fill in, never commit)
```

## Setup

### 1. Requirements

- Java 21+ and Maven
- Microsoft Edge, with `msedgedriver.exe` installed via WinGet (`Hooks.java`
  points at the WinGet install path directly:
  `%USERPROFILE%\AppData\Local\Microsoft\WinGet\Packages\Microsoft.EdgeDriver_Microsoft.Winget.Source_8wekyb3d8bbwe\msedgedriver.exe`).
  If your driver lives elsewhere, update that path in `Hooks.java`.
- The AI triage service running locally and reachable — see
  [`TTS Tool/README.md`](../TTS%20Tool/README.md) for full setup (Ollama +
  models + the service itself). Without it running, every failure's AI
  diagnosis attachment will just say the service is unavailable — the tests
  themselves still run fine.
- The triage SDK installed to your local Maven repo:
  ```bash
  cd "../TTS Tool SDK"
  mvn install
  ```

### 2. Set up local secrets

This project keeps test credentials and PII **out of feature files and
`config.properties` entirely**. Feature files reference `{env:KEY}`
placeholders, resolved at runtime from a local `.env` file that is never
committed:

```bash
cp .env.example .env
```

Then fill in `.env`:

```
TEST_USERNAME=
TEST_PASSWORD=
TEST_NATIONAL_ID=
TEST_FIRST_NAME=
TEST_LAST_NAME=
```

`Hooks.resolve(...)` reads `{env:TEST_USERNAME}`-style placeholders out of
feature files and looks them up in `.env` at runtime — a missing key throws
immediately with a clear error rather than silently running with a blank
value. `TEST_NATIONAL_ID`/`TEST_FIRST_NAME`/`TEST_LAST_NAME` are only used for
the RED-line purchase flow's buyer-details step; the deliberately-invalid
national ID values in that feature's negative-test scenarios are left literal
in the feature file since they're the invalid input under test, not real PII.

### 3. Run it

```bash
mvn test
```

This runs everything except `@triageToolVerification`. Reports:
- Cucumber HTML report: `target/cucumber-reports/report.html`
- Allure results: `target/allure-results` (`mvn allure:report` / `allure serve` to view)

## Configuration (`config.properties`)

| Key | Meaning |
|---|---|
| `base.url` | The e-shop URL to start each scenario from |
| `triage.service.url` | Full URL of the triage service's endpoint, e.g. `http://localhost:8787/api/triage` |
| `triage.project.id` | `vf-eshop-test` — keeps this suite's failure history separate from other projects using the same triage service |

## How the AI triage integration works here

`Hooks.java` constructs a `TriageSdk` in `@Before` and calls
`triageSdk.onStepFailure(scenario, driver)` in `@AfterStep`. On any failed
step, the SDK captures a screenshot and the page source, sends both plus the
real exception to the triage service, and attaches the screenshot, page
source, and the AI's diagnosis back onto the Cucumber scenario — visible in
the Allure report.

This **requires** `com.aitriage.sdk.TriageEventListener` to be registered in
`TestRunner`'s `@CucumberOptions(plugin = {...})` list — it's how the SDK gets
access to the real failing exception, which Cucumber's `Scenario` object
doesn't expose directly. If that listener is ever removed, tests keep passing
and diagnoses keep getting created, but every one of them will show exception
type `Unknown` with an empty message — a silent data-quality bug, not a
crash.
