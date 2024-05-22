@REQ_JFCFG-212
Feature: Login
  Scenario Outline: Login with valid credentials
    Given I am on the login page
    When I enter "<username>" and "<password>"
    And I click on the login button
    Then I should be logged in
    And I should see the welcome message "<welcome_message>"

    Examples:
      | username | password | welcome_message |
      | admin    | password | Welcome user1   |