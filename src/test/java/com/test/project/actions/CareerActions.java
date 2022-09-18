package com.test.project.actions;

import com.test.project.utilites.helper.SeleniumHelper;
import org.openqa.selenium.WebElement;
import org.testng.Assert;

import java.util.ArrayList;
import java.util.List;


public class CareerActions {

    private final SeleniumHelper seleniumHelper;

    public CareerActions(SeleniumHelper seleniumHelper) {

        this.seleniumHelper = seleniumHelper;
    }

    public void findTeam(String teamName) {

        boolean flag = false;
        List<WebElement> list = new ArrayList<>(seleniumHelper.findElements("CAREERS_teams_items_title"));
        for (int i = 0; i < list.size(); i++){

            if (teamName.equalsIgnoreCase(list.get(i).getText())) {

                seleniumHelper.forceClickToElement(seleniumHelper.findElements("CAREERS_teams_items_title").get(i));
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag, "The team has not on the team list! '"+ teamName+ "'");
    }

    public void findLocation(String location) {

        boolean flag = false;
        List<WebElement> list = new ArrayList<>(seleniumHelper.findElements("CAREERS_location_dropdown_option"));
        for (int i = 0; i < list.size(); i++){

            if (location.equalsIgnoreCase(list.get(i).getText())) {

                seleniumHelper.findElements("CAREERS_location_dropdown_option").get(i).click();
                flag = true;
                break;
            }
        }
        Assert.assertTrue(flag, "The location has not on the location list! '"+ location+ "'");
    }
}
