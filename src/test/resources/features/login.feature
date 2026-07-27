Feature: eShop Login Flow

  Scenario Outline: Login to the eShop with the site set to a specific language
    Given the user opens the eShop website
    When the user sets the site language to "<language>"
    Then the login icon should be on the "<side>" side of the page
    When the user clicks the login icon
    And the user logs in with username "<username>" and password "<password>"
    Then the user should be redirected back to the eShop homepage

    Examples:
      | language | side  | username    | password |
      | English  | right | 01020083131 | Akh_2112 |
      | Arabic   | left  | 01020083131 | Akh_2112 |
