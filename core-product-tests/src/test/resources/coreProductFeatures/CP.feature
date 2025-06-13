@cp
Feature: Capture Jackets data from Shop Men's section

  @cp1
  Scenario: Test Case CP 1 : Store all Jacket details and attach to report
    Given navigate to the CP home page URL "cpUrl"
    When the user hovers on the Shop Menu item and clicks on "Men's"
    And extract the title, price, and top seller message for each jacket from all pages
    Then  store the extracted jacket details in a text file
    And attach the text file to the test report

  @cp2
  Scenario: Test Case CP 2 : Count video feeds under News & Features section
    Given navigate to the CP home page URL "cpUrl"
    When the user hovers on the menu item and clicks on "News & Features"
    Then count the total number of video feeds on the page
    And count the number of video feeds labeled as ">= 3D"

