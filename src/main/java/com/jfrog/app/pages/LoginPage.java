package com.jfrog.app.pages;

import com.microsoft.playwright.Page;

public class LoginPage {

    private Page page;

    //locators
    private String username = "input[name='username']";
    private String password = "input[name='password']";
    private String loginButton = "button[type='submit']";
    public LoginPage(Page page) {
        this.page = page;
    }

    public void loginToLNP(String lnpusername, String lnppassword) {
        page.fill(username, lnpusername);
        page.fill(password, lnppassword);
        page.click(loginButton);
    }
}
