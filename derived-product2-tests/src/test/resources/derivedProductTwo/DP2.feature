@nba
Feature: Detect Duplicate Footer Hyperlinks and Export to CSV

  @dp2
  Scenario: Validate that all hyperlinks in the footer section are unique and export them to a CSV file. 
    Given the user navigates to the DP2 home page "dp2Url"
    When the user scroll down to the footer
    And export all the hyperlinks of the Footer links into a CSV file 
    And report if any duplicate hyperlinks are present
      