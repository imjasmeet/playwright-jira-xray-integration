@REQ_JFCFG-292
Feature: Login functionality on a test instance

  @id:1 @L1 @l0
  Scenario Outline: Test login functionality with valid credentials on test instance
    Given I am on the login page
    When I enter "<username>" and "<password>"
    And I click on the login button
    Then I should be logged in
    And I should see the welcome message "<welcome_message>"

    Examples:
      | username | password   | welcome_message          |
      | jasmeets | Password@1 | Welcome to the Home page |
      | admin    | password   | Welcome to the Home page |

  @id:2
  Scenario Outline: Test login functionality with invalid credentials on test instance
    Given I am on the login page
    When I enter "<username>" and "<password>"
    And I click on the login button
    Then I should be logged in
    And I should see the error message "<error_message>"

    Examples:
      | username | password   | error_message                                                       |
      | admin    | Password@1 | Login has failed. Due to Incorrect username/password or locked user |
