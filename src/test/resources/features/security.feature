Feature: eShop Security Checks

  # S-005: protected routes must not be reachable without authentication
  Scenario: S-005 - Protected cart route redirects unauthenticated users to login
    Given the user opens the eShop website
    When the user directly navigates to the "/en/cart" page without logging in
    Then the user should be redirected to the login page instead of the cart

  # S-007: the session identifier must rotate after a successful login
  Scenario Outline: S-007 - Session identifier rotates after successful login
    Given the user opens the eShop website
    When the user records the current session cookies
    And the user clicks the login icon
    And the user logs in with username "<username>" and password "<password>"
    Then the session cookies should have changed since login began

    Examples:
      | username            | password            |
      | {env:TEST_USERNAME} | {env:TEST_PASSWORD} |

  # S-015: transport security and baseline response headers
  Scenario: S-015 - HTTPS is enforced with security headers present
    Then the eShop site should enforce HTTPS with security headers

  # S-018: third-party resources must load from secure origins only
  Scenario: S-018 - Third-party resources load only from secure origins
    Given the user opens the eShop website
    Then all externally loaded scripts on the homepage should use secure origins
