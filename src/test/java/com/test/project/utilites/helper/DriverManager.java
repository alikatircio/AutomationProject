package com.test.project.utilites.helper;

import com.test.project.utilites.PropertyManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    public static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public static WebDriver getDriver() {

        return driver.get();
    }

    public static void createDriver(String browser) {
        try {

            switch (browser) {
                case "firefox":

                    WebDriverManager.firefoxdriver().setup();
                    driver.set(new FirefoxDriver());
                    break;

                default:

                    WebDriverManager.chromedriver().setup();
                    driver.set(new ChromeDriver());
                    break;
            }
        } catch (Throwable e) {

            e.printStackTrace();
        }
        getDriver().manage().window().maximize();
        getDriver().manage().deleteAllCookies();
        new PropertyManager();
        String baseUrl =  PropertyManager.getProperty("baseUrl");
        getDriver().get(baseUrl);
    }

    public static void stopDriver() {

        getDriver().quit();
    }
}
