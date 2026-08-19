@triageToolVerification
Feature: Triage Tool Verification

  # NOT a product regression suite. Every scenario below is designed to fail in a distinct,
  # controlled way so we can verify the AI triage tool classifies each failure type correctly.
  # Excluded from normal `mvn test` runs (see TestRunner). Run explicitly with:
  #   mvn test "-Dcucumber.filter.tags=@triageToolVerification"

  Scenario: TRIAGE-01 - Nonexistent locator produces a LOCATOR_BROKEN diagnosis
    Given the user opens the eShop website cleanly
    Then a nonexistent element locator should fail to be found

  Scenario: TRIAGE-02 - Overlay-blocked click produces a UI_OVERLAY_BLOCKING diagnosis
    Given the user opens the eShop website without dismissing overlays
    Then clicking an element hidden behind the overlay should fail

  Scenario: TRIAGE-03 - Broken page load produces a SITE_RENDERING_ISSUE diagnosis
    Given the user navigates to a non-existent page path
    Then an element that only exists on a real page should be visible

  Scenario: TRIAGE-04 - Explicit value mismatch produces an ASSERTION_MISMATCH diagnosis
    Given the user opens the eShop website cleanly
    Then the page title should incorrectly equal "this-value-will-never-match"

  Scenario: TRIAGE-05 - Ambiguous failure with no page evidence produces an UNKNOWN diagnosis
    Then a deliberately ambiguous failure occurs with no page context
