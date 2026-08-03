Feature: RED Line Purchase Flow

  Scenario Outline: Purchase a RED line with a chosen sim type and plan, then remove it from the cart
    Given the user opens the eShop website and logs in with username "<username>" and password "<password>"
    When the user clicks on "RED lines" from Shop by Category
    And the user selects "<simType>" for the RED line
    And the user selects any available RED line number
    And the user clicks Choose This Line
    And the user selects the "<plan>" plan
    And the user selects "Myself" from the who are you buying for dropdown
    And the user enters national id "<nationalId>" first name "<firstName>" and last name "<lastName>"
    And the user clicks Next on the buyer details form
    And the user checks the first payment support document option
    And the user clicks Checkout on the RED line order
    Then the user removes the items from the cart
    And the cart should be empty

    Examples:
      | simType  | plan           | username            | password            | nationalId              | firstName            | lastName            |
      | Sim Card | RED ESSENTIAL+ | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | {env:TEST_NATIONAL_ID} | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |
      | eSim     | RED ADVANCE+   | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | {env:TEST_NATIONAL_ID} | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |
      | Sim Card | RED PRIME+     | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | {env:TEST_NATIONAL_ID} | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |

  # F-010: Choose This Line must stay disabled until a number is selected
  Scenario Outline: Choose This Line stays disabled without a selected number
    Given the user opens the eShop website and logs in with username "<username>" and password "<password>"
    When the user clicks on "RED lines" from Shop by Category
    And the user selects "<simType>" for the RED line
    Then Choose This Line should remain disabled

    Examples:
      | simType  | username            | password            |
      | Sim Card | {env:TEST_USERNAME} | {env:TEST_PASSWORD} |

  # F-014: a plan outside the supported catalog must not be selectable
  Scenario Outline: An unsupported RED plan is not available
    Given the user opens the eShop website and logs in with username "<username>" and password "<password>"
    When the user clicks on "RED lines" from Shop by Category
    And the user selects "<simType>" for the RED line
    And the user selects any available RED line number
    And the user clicks Choose This Line
    Then the "<plan>" plan should not be available

    Examples:
      | simType  | username            | password            | plan           |
      | Sim Card | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | RED UNLIMITED+ |

  # F-019 / S-001: buyer details form must stay locked behind invalid or incomplete data
  # nationalId is left literal here (blank / too-short / non-numeric / too-long) since these
  # values are the deliberately invalid input under test, not real PII.
  Scenario Outline: The buyer details Next button stays disabled with invalid data
    Given the user opens the eShop website and logs in with username "<username>" and password "<password>"
    When the user clicks on "RED lines" from Shop by Category
    And the user selects "<simType>" for the RED line
    And the user selects any available RED line number
    And the user clicks Choose This Line
    And the user selects the "<plan>" plan
    And the user selects "Myself" from the who are you buying for dropdown
    And the user enters national id "<nationalId>" first name "<firstName>" and last name "<lastName>"
    Then the Next button on the buyer details form should remain disabled

    Examples:
      | simType  | plan           | username            | password            | nationalId          | firstName            | lastName            |
      | Sim Card | RED ESSENTIAL+ | {env:TEST_USERNAME} | {env:TEST_PASSWORD} |                     | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |
      | Sim Card | RED ESSENTIAL+ | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | 123                 | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |
      | Sim Card | RED ESSENTIAL+ | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | abcdefghijklm       | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |
      | Sim Card | RED ESSENTIAL+ | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | 304122121003599999  | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |

  # F-022: cannot proceed to checkout without choosing a payment support option
  Scenario Outline: Checkout stays disabled without a payment support option
    Given the user opens the eShop website and logs in with username "<username>" and password "<password>"
    When the user clicks on "RED lines" from Shop by Category
    And the user selects "<simType>" for the RED line
    And the user selects any available RED line number
    And the user clicks Choose This Line
    And the user selects the "<plan>" plan
    And the user selects "Myself" from the who are you buying for dropdown
    And the user enters national id "<nationalId>" first name "<firstName>" and last name "<lastName>"
    And the user clicks Next on the buyer details form
    Then the Checkout button should remain disabled

    Examples:
      | simType  | plan           | username            | password            | nationalId              | firstName            | lastName            |
      | Sim Card | RED ESSENTIAL+ | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | {env:TEST_NATIONAL_ID} | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |

  # S-013: the National ID must never leak into the page URL
  Scenario Outline: National ID is not exposed in the page URL
    Given the user opens the eShop website and logs in with username "<username>" and password "<password>"
    When the user clicks on "RED lines" from Shop by Category
    And the user selects "<simType>" for the RED line
    And the user selects any available RED line number
    And the user clicks Choose This Line
    And the user selects the "<plan>" plan
    And the user selects "Myself" from the who are you buying for dropdown
    And the user enters national id "<nationalId>" first name "<firstName>" and last name "<lastName>"
    Then the current page URL should not contain the national id "<nationalId>"

    Examples:
      | simType  | plan           | username            | password            | nationalId              | firstName            | lastName            |
      | Sim Card | RED ESSENTIAL+ | {env:TEST_USERNAME} | {env:TEST_PASSWORD} | {env:TEST_NATIONAL_ID} | {env:TEST_FIRST_NAME} | {env:TEST_LAST_NAME} |
