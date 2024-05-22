package com.jfrog.app.hooks;

import com.jfrog.app.factory.BrowserFactory;
import com.microsoft.playwright.Page;
import io.cucumber.java.After;
import io.cucumber.java.Before;

public class Hooks {


    private static BrowserFactory browserFactory;
    private static Page page;

    @Before
    public void navigateToURL() {
        System.out.println("Navigating to the URL");
        browserFactory = new BrowserFactory();
        page = browserFactory.initBrowser(browserFactory.init_prop());

    }

    @After
    public void closeBrowser() {
        page.context().browser().close();
    }

    public static Page getPage() {
        return page;
    }
}
