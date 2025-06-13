Feature: Validate slide titles and playback durations below Tickets menu on DP1 site

  @dp1
  Scenario: Verify the number of slides, their titles, and playback duration
    Given the user navigates to the DP1 home page "dp1Url"
    Then count the number of slides present below the "Tickets" menu
    And compare the title of all slides with title from input data
    And validate each slide is playing for at least "10" seconds

      