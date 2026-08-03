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
      | simType  | plan           | username    | password | nationalId     | firstName | lastName |
      | Sim Card | RED ESSENTIAL+ | 01020083131 | Akh_2112 | 30412212100359 | Ali       | Khaled   |
      | eSim     | RED ADVANCE+   | 01020083131 | Akh_2112 | 30412212100359 | Ali       | Khaled   |
      | Sim Card | RED PRIME+     | 01020083131 | Akh_2112 | 30412212100359 | Ali       | Khaled   |

  # F-010: Choose This Line must stay disabled until a number is selected
  Scenario Outline: Choose This Line stays disabled without a selected number
    Given the user opens the eShop website and logs in with username "<username>" and password "<password>"
    When the user clicks on "RED lines" from Shop by Category
    And the user selects "<simType>" for the RED line
    Then Choose This Line should remain disabled

    Examples:
      | simType  | username    | password |
      | Sim Card | 01020083131 | Akh_2112 |

  # F-014: a plan outside the supported catalog must not be selectable
  Scenario Outline: An unsupported RED plan is not available
    Given the user opens the eShop website and logs in with username "<username>" and password "<password>"
    When the user clicks on "RED lines" from Shop by Category
    And the user selects "<simType>" for the RED line
    And the user selects any available RED line number
    And the user clicks Choose This Line
    Then the "<plan>" plan should not be available

    Examples:
      | simType  | username    | password | plan           |
      | Sim Card | 01020083131 | Akh_2112 | RED UNLIMITED+ |

  # F-019 / S-001: buyer details form must stay locked behind invalid or incomplete data
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
      | simType  | plan           | username    | password | nationalId         | firstName | lastName |
      | Sim Card | RED ESSENTIAL+ | 01020083131 | Akh_2112 |                     | Ali       | Khaled   |
      | Sim Card | RED ESSENTIAL+ | 01020083131 | Akh_2112 | 123                 | Ali       | Khaled   |
      | Sim Card | RED ESSENTIAL+ | 01020083131 | Akh_2112 | abcdefghijklm       | Ali       | Khaled   |
      | Sim Card | RED ESSENTIAL+ | 01020083131 | Akh_2112 | 304122121003599999  | Ali       | Khaled   |

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
      | simType  | plan           | username    | password | nationalId     | firstName | lastName |
      | Sim Card | RED ESSENTIAL+ | 01020083131 | Akh_2112 | 30412212100359 | Ali       | Khaled   |

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
      | simType  | plan           | username    | password | nationalId     | firstName | lastName |
      | Sim Card | RED ESSENTIAL+ | 01020083131 | Akh_2112 | 30412212100359 | Ali       | Khaled   |
