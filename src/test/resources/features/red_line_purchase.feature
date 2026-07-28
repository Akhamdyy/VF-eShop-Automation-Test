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

    Examples:
      | simType  | plan           | username    | password | nationalId     | firstName | lastName |
      | Sim Card | RED ESSENTIAL+ | 01020083131 | Akh_2112 | 30412212100359 | Ali       | Khaled   |
      | eSim     | RED ADVANCE+   | 01020083131 | Akh_2112 | 30412212100359 | Ali       | Khaled   |
      | Sim Card | RED PRIME+     | 01020083131 | Akh_2112 | 30412212100359 | Ali       | Khaled   |
