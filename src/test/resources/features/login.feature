Feature: eShop Login Flow

  Scenario Outline: Login to the eShop with the site set to a specific language
    Given the user opens the eShop website
    When the user sets the site language to "<language>"
    Then the login icon should be on the "<side>" side of the page
    When the user clicks the login icon
    And the user logs in with username "<username>" and password "<password>"
    Then the user should be redirected back to the eShop homepage

    Examples:
      | language | side  | username           | password           |
      | English  | right | {env:TEST_USERNAME} | {env:TEST_PASSWORD} |
      | Arabic   | left  | {env:TEST_USERNAME} | {env:TEST_PASSWORD} |

  # F-003: language can be switched from the homepage without logging in
  Scenario Outline: Switch the site language without logging in
    Given the user opens the eShop website
    When the user sets the site language to "<language>"
    Then the login icon should be on the "<side>" side of the page

    Examples:
      | language | side  |
      | English  | right |
      | Arabic   | left  |

  # F-002: invalid credentials must be rejected with no session created
  Scenario Outline: Login with invalid credentials is rejected
    Given the user opens the eShop website
    When the user clicks the login icon
    And the user logs in with username "<username>" and password "<password>"
    Then the login should be rejected and no session should be created

    Examples:
      | username    | password       |
      | 01000000000 | WrongPass123   |
