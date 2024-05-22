package com.jfrog.app.factory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.Properties;

import com.microsoft.playwright.Browser;
import com.microsoft.playwright.BrowserContext;
import com.microsoft.playwright.BrowserType;
import com.microsoft.playwright.BrowserType.LaunchOptions;
import com.microsoft.playwright.Page;
import com.microsoft.playwright.Playwright;

public class BrowserFactory {

    Playwright playwright;
    Browser browser;
    BrowserContext browserContext;
    Page page;
    Properties prop;

    private static ThreadLocal<Browser> threadLoaclBrowser = new ThreadLocal<>();
    private static ThreadLocal<BrowserContext> threadLocalBrowserContext = new ThreadLocal<>();
    private static ThreadLocal<Page> threadLocalPage = new ThreadLocal<>();
    private static ThreadLocal<Playwright> threadLocalPlaywright = new ThreadLocal<>();

    public static Playwright getPlaywright() {
        return threadLocalPlaywright.get();
    }

    public static Browser getBrowser() {
        return threadLoaclBrowser.get();
    }

    public static BrowserContext getBrowserContext() {
        return threadLocalBrowserContext.get();
    }

    public static Page getPage() {
        return threadLocalPage.get();
    }

    public Page initBrowser(Properties prop) {

        String browserName = prop.getProperty("browser").trim();
        System.out.println("Executing on browser : " + browserName);

        threadLocalPlaywright.set(Playwright.create());

        switch (browserName.toLowerCase()) {

            case "chrome":
                threadLoaclBrowser.set(
                        getPlaywright().chromium().launch(new LaunchOptions().setChannel("chrome").setHeadless(Boolean.parseBoolean(prop.getProperty("headless").trim()))));
            case "firefox":
                threadLoaclBrowser.set(getPlaywright().firefox().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless").trim()))));
                break;
            case "safari":
                threadLoaclBrowser.set(getPlaywright().webkit().launch(new BrowserType.LaunchOptions().setHeadless(Boolean.parseBoolean(prop.getProperty("headless").trim()))));
                break;

            default:
                System.out.println("Invalid browser name provided");
                break;
        }

        threadLocalBrowserContext.set(getBrowser().newContext());
        threadLocalPage.set(getBrowserContext().newPage());
        getPage().navigate(prop.getProperty("url").trim());
        return getPage();

    }

    public Properties init_prop() {

        try {
            FileInputStream fip = new FileInputStream("./src/test/resources/config.properties");
            if (fip == null) {
                throw new FileNotFoundException("Property file 'config.properties' not found in the classpath");
            }
            prop = new Properties();
            prop.load(fip);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return prop;

    }


    public static String takeScreenshot() {
        String path = System.getProperty("user.dir") + "/screenshot/" + System.currentTimeMillis() + ".png";

        byte[] buffer = getPage().screenshot(new Page.ScreenshotOptions().setPath(Paths.get(path)).setFullPage(true));
        String base64Path = Base64.getEncoder().encodeToString(buffer);

        return base64Path;
    }

}
