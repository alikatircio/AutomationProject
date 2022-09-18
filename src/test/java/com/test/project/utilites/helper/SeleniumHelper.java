package com.test.project.utilites.helper;


import com.test.project.utilites.LoggerManager;
import com.test.project.utilites.model.ElementInfo;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;


import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.fail;
import static org.testng.AssertJUnit.*;
import static org.testng.AssertJUnit.assertEquals;


public class SeleniumHelper {
    private static WebDriver driver;

    public SeleniumHelper(WebDriver driver) {
        SeleniumHelper.driver = driver;
    }

    public void waitByMilliSeconds(long milliseconds) {
        try {
            Thread.sleep(milliseconds);
        } catch (InterruptedException e) {

            e.printStackTrace();
        }
    }

    public WebElement findElement(String key) {

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);

        WebDriverWait webDriverWait = new WebDriverWait(driver, 90, 120);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.visibilityOfElementLocated(infoParam));

        scrollElementWithWebElement(webElement);
        return webElement;
    }

    public WebElement findElementForNot(String key) {

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);

        WebDriverWait webDriverWait = new WebDriverWait(driver, 2, 120);
        WebElement webElement = webDriverWait
                .until(ExpectedConditions.visibilityOfElementLocated(infoParam));

        scrollElementWithWebElement(webElement);
        return webElement;
    }


    public List<WebElement> findElements(String key) {

        ElementInfo elementInfo = StoreHelper.INSTANCE.findElementInfoByKey(key);
        By infoParam = ElementHelper.getElementInfoToBy(elementInfo);

        int loopCount = 0;
        while (loopCount < 30) {

            waitByMilliSeconds(1000);
            if (driver.findElements(infoParam).size() > 0) {

                break;
            }
            loopCount++;
        }
        if (driver.findElements(infoParam).size() <= 0) {

            fail("' " + key + " '" + " => The element not created");
        }
        return driver.findElements(infoParam);
    }

    public void clickElement(String key) {

        findElement(key).click();
    }

    public void forceClickToElement(WebElement element) {

        scrollElementWithWebElement(element);
        WebDriverWait wait = new WebDriverWait(driver, 120, 300);
        wait.until(ExpectedConditions.elementToBeClickable(element));
        ((JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }

    public void scrollElement(String key) {

        WebElement webElement = findElement(key);
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center'})", webElement);
        LoggerManager.info("' " + key + " '" + " => Element JS with scroll");
    }

    public void scrollElementWithWebElement(WebElement element) {

        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].scrollIntoView({block: 'center', inline: 'nearest'})", element);
        LoggerManager.info("Element JS with scroll");
    }


    public void checkElementDisplayed(String action, String key) {

        if (action.equals("not")) {
            try {

                boolean isDisplayed = findElementForNot(key).isDisplayed();
                LoggerManager.info("' " + key + " ' " + " ' " + isDisplayed + " '" + " => Is displayed element");
                assertFalse("' " + key + " ' " + " ' " + isDisplayed + " '" + " => Is displayed element", isDisplayed);
            } catch (TimeoutException e) {

                LoggerManager.info("' " + key + " '" + " => Searched element is not visible");
            }
        } else {
            try {

                boolean isDisplayed = findElement(key).isDisplayed();
                LoggerManager.info("' " + key + " ' " + " ' " + isDisplayed + " '" + " => Is displayed element");
                assertTrue("' " + key + " ' " + " ' " + isDisplayed + " '" + " => Is displayed element", isDisplayed);
            } catch (TimeoutException e) {

                fail("' " + key + " '" + " => Searched element is not visible");
            }
        }
    }


    public void getTextContains(String key, String action, String action2, String text) {

        String getText = findElement(key).getText();
        LoggerManager.info("' " + key + " ' " + " ' " + getText + " '" + " => Element text");

        if (action.equals("not")) {
            if (action2.equals("equals")) {

                assertNotEquals("Expected text and are the same with actual" + " Expected: " + text + " Actual: " + getText, getText, text);
            } else {

                assertFalse("Expected text and are the same with actual" + " Expected: " + text + " Actual: " + getText, getText.contains(text));
            }
        } else {
            if (action2.equals("equals")) {

                assertEquals("Expected text and are not the same with actual" + " Expected: " + text + " Actual: " + getText, getText, text);
            } else {

                assertTrue("Expected text and are not the same with actual" + " Expected: " + text + " Actual: " + getText, getText.contains(text));
            }
        }
    }

    public void assertStringToString(String act, String exp, String actionNot, String actionContains) {

        if (actionNot.equals("not")) {
            if (actionContains.equals("equals")) {

                assertNotEquals("Expected text and are the same with actual" + " Expected: " + exp + " Actual: " + act, exp, act);
            } else {

                assertFalse("Expected text and are the same with actual" + " Expected: " + exp + " Actual: " + act, act.contains(exp));
            }
        } else {
            if (actionContains.equals("equals")) {

                assertEquals("Expected text and are not the same with actual" + " Expected: " + exp + " Actual: " + act, exp, act);
            } else {

                assertTrue("Expected text and are not the same with actual" + " Expected: " + exp + " Actual: " + act, act.contains(exp));
            }
        }
        LoggerManager.info("Exp " + exp + " Act " + act);
    }

    /**
     * 'not' is for page title contains parameterTitle, it is get an error
     *
     * @param action for assertion
     * @param title  for text
     */
    public void checkTitleContains(String action, String title) {

        String getTitle = driver.getTitle();
        LoggerManager.info("' " + getTitle + " '" + " => Page title");

        if (action.equals("not")) {

            assertFalse("Expected title and are the same with actual" + " Expected: " + title + " Actual: " + getTitle, getTitle.contains(title));
        } else {

            assertTrue("Expected title and are not the same with actual" + " Expected: " + title + " Actual: " + getTitle, getTitle.contains(title));
        }
    }

    public void checkGetTextList(String action, String key, String action2, String action3, String keyList) {

        String getText = "";
        ArrayList<String> strList = new ArrayList<>();
        if (action.equals("text")) {

            getText = key;
            LoggerManager.info("' " + key + " '" + " => Key text");

        } else if (action.equals("element")) {

            getText = findElement(key).getText();
            LoggerManager.info("' " + key + " ' " + " ' " + getText + " '" + " => Element text");
        }

        List<WebElement> elementKey = findElements(keyList);
        int elementSize = elementKey.size();
        LoggerManager.info("' " + keyList + " ' " + " ' " + elementSize + " '" + " => Element count");

        if (action3.equals("inside")) {
            for (WebElement elementText : elementKey) {

                String countGetText = elementText.getText();

                strList.add(countGetText);
                LoggerManager.info("' " + countGetText + " '" + " => List element text" + "\n");
            }
            for (int i = 0; strList.size() > i; i++) {
                if (action2.equals("not")) {

                    assertFalse("Expected element text and are the same with actual" + " Expected: " + getText + " Actual: " + strList, strList.contains(getText));
                } else {

                    assertTrue("Expected element text and are not the same with actual" + " Expected: " + getText + " Actual: " + strList, strList.contains(getText));
                }
            }
        } else if (action3.equals("same as")) {
            for (WebElement webElement : elementKey) {

                String countGetText = webElement.getText();
                LoggerManager.info("' " + countGetText + " '" + " => List element text" + "\n");

                if (action2.equals("not")) {

                    assertFalse("Expected element text and are the same with actual" + " Expected: " + getText + " Actual: " + countGetText, countGetText.contains(getText));
                } else {

                    assertTrue("Expected element text and are not the same with actual" + " Expected: " + getText + " Actual: " + countGetText, countGetText.contains(getText));
                }
            }
        }
    }


    public void checkURLContains(String action, String expectedURL) {

        String getCurrentUrl = driver.getCurrentUrl();
        LoggerManager.info("' " + getCurrentUrl + " '" + " => Page current url");

        if (action.equals("not")) {

            assertFalse("Expected URL and are the same with actual" + " Expected: " + expectedURL + " Actual: " + getCurrentUrl, getCurrentUrl.contains(expectedURL));
        } else {

            assertTrue("Expected URL and are not the same with actual" + " Expected: " + expectedURL + " Actual: " + getCurrentUrl, getCurrentUrl.contains(expectedURL));
        }
    }

    public String getAttribute(String key, String attr) {

        String value = findElement(key).getAttribute(attr);
        LoggerManager.info("' " + key + " ' " + " ' " + value + " '" + " => Print attribute element");
        return value;
    }

    public void selectDropdown(String key, String visibleText) {

        scrollElement(key);
        Select location = new Select(findElement(key));
        location.selectByVisibleText(visibleText);
        LoggerManager.info("' " + key + " ' " + " ' " + visibleText + " '" + " => Select dropdown");
    }

    public void hoverElement(String key) {

        WebElement webElement = findElement(key);
        Actions actions = new Actions(driver);
        actions.moveToElement(webElement).build().perform();
        LoggerManager.info("' " + key + " '" + " => Element hover");
    }

    public void switchToTab(int tabNumber) {

        Set<String> winHandleBeforeSet = driver.getWindowHandles();
        ArrayList<String> tabList = new ArrayList<>(winHandleBeforeSet);

        driver.switchTo().window(tabList.get(tabNumber));
        LoggerManager.info("' " + tabNumber + " '" + " => Switch to tab window");
    }

}
