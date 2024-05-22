package com.jfrog.app.stepdefs;


import com.jfrog.app.factory.BrowserFactory;
import com.jfrog.app.pages.LoginPage;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import com.jfrog.app.hooks.Hooks;
import com.jfrog.app.pages.LoginPage;
public class LoginStepDefinitions {

    public LoginPage loginPage;
     public LoginStepDefinitions(){
         this.loginPage = new LoginPage(Hooks.getPage());
    }

    @Given("I am on the login page")
    public void i_am_on_the_login_page() {
        System.out.println("I am on the login page");
    }

    @When("I enter {string} and {string}")
    public void i_enter_and(String username, String password) {
        loginPage.loginToLNP(username, password);
    }

    @When("I click on the login button")
    public void i_click_on_the_login_button() {
        System.out.println("I clcik on the login button");
    }

    @Then("I should be logged in")
    public void i_should_be_logged_in() {
        System.out.println("I should be logged in");
    }

    @Then("I should see the welcome message {string}")
    public void i_should_see_the_welcome_message(String welcomeMessage) {
        System.out.println("I saw successful login message");
    }
}
