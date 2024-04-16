package com.jfrog.app;

import com.jfrog.app.factory.BrowserFactory;
import com.jfrog.app.pages.LoginPage;
import com.microsoft.playwright.Page;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import java.util.Properties;

public class BaseTestClass {
    BrowserFactory bf;
    Page page;
    protected Properties prop;

    protected LoginPage loginPage;

    @Parameters({ "browser" })
    @BeforeTest
    public void setup(String browserName) {
        bf = new BrowserFactory();

        prop = bf.init_prop();

        if (browserName != null) {
            prop.setProperty("browser", browserName);
        }

        page = bf.initBrowser(prop);
        loginPage = new LoginPage(page);
    }

    @AfterTest
    public void tearDown() {
        page.context().browser().close();
    }
}
