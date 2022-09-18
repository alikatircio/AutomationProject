package com.test.project.utilites.helper;

import com.test.project.utilites.model.ElementInfo;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class WaitHelper {

    private static WebDriver driver;

    public WaitHelper(WebDriver driver) {
        WaitHelper.driver = driver;
    }

    public void waitUntil(String key, String progress) {

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);
        WebDriverWait webDriverWait = new WebDriverWait(driver, 300, 300);

        switch (progress) {
            case "visible":
                webDriverWait
                        .until(ExpectedConditions.visibilityOfElementLocated(infoParam));
                break;
        }

    }
}
