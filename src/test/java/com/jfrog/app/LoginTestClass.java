package com.jfrog.app;

import org.testng.annotations.Test;

public class LoginTestClass extends BaseTestClass{

    @Test
    public void loginTest() {
        loginPage.loginToLNP(prop.getProperty("username"), prop.getProperty("password"));
    }

    }