Feature: Adding items to cart and verifying sub total

  Background:
    Given I open a Amazon website

    @amzmonitor
    Scenario Outline: Adding a monitor item to the cart and verifying sub total
      When I search for "<item>"
      And I select the first item from the results list
      And I add first item to cart
      Then I should see the price identical to the product page
      And I should see the sub total identical to the product page
      Examples:
      | item    |
      | Monitor |

  @amzlaptop
  Scenario Outline: Adding a laptop item to the cart and verifying sub total
    When I search for "<item>"
    And I select the second item from the results list
    And I add second item to cart
    Then I should see the price identical to the second product page
    And I should see the sub total identical to the product page
    Examples:
      | item   |
      | Laptop |

  @amzitems
  Scenario Outline: Adding a multiple items to the cart and verifying sub total
    When I search for "<first_search>"
    And I select the first item from the results list
    And I add first searched item to cart
    When I search for "<second_search>"
    And I select the first item from the results list
    Then I add second searched item to cart
    And I should see the sub total of all items
    Examples:
      | first_search | second_search |
      | Headphones   | Keyboard      |

  @flpmonitor
  Scenario Outline: Adding a monitor product to the cart and verifying sub total in Flipkart
    Given I open the Flipkart website
    When I search for "<item>" on Flipkart
    And I select the first item from the Flipkart results list
    And I add the first item to the Flipkart cart
    Then I should see the Flipkart price identical to the product page
    Examples:
      | item    |
      | Monitor |