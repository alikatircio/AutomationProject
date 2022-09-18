package com.test.project.scenarios;

import com.test.project.actions.CareerActions;
import com.test.project.utilites.helper.SeleniumHelper;
import com.test.project.utilites.helper.WaitHelper;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.HashMap;
import java.util.Locale;


public class ScenarioSearchJob {

    WebDriver driver;
    SeleniumHelper seleniumHelper;
    CareerActions careerActions;
    WaitHelper waitHelper;


    public ScenarioSearchJob(WebDriver driver, SeleniumHelper seleniumHelper, WaitHelper waitHelper) {

        this.driver = driver;
        this.seleniumHelper = seleniumHelper;
        this.waitHelper = waitHelper;
        this.careerActions = new CareerActions(seleniumHelper);
        PageFactory.initElements(driver, careerActions);
    }

    public void step1() {

        seleniumHelper.checkTitleContains("", "Insider personalization engine for seamless customer experiences");
    }

    public void step2() {

        seleniumHelper.clickElement("MENU_more");
        seleniumHelper.clickElement("MENU_career");
        seleniumHelper.checkTitleContains("", "Careers");
        seleniumHelper.checkURLContains("", "careers");
        seleniumHelper.checkElementDisplayed("", "CAREERS_teams");
        seleniumHelper.checkElementDisplayed("", "CAREERS_teams_items");
        seleniumHelper.checkElementDisplayed("", "CAREERS_locations");
        seleniumHelper.checkElementDisplayed("", "CAREERS_locations_destinations");
        seleniumHelper.checkElementDisplayed("", "CAREERS_life_insider_title");
        seleniumHelper.checkElementDisplayed("", "CAREERS_life_insider_desc");
        seleniumHelper.getTextContains("CAREERS_life_insider_title", "", "equals", "Life at Insider");
        seleniumHelper.getTextContains("CAREERS_life_insider_desc", "", "equals", "We’re here to grow and drive growth—as none of us " +
                "did before. Together, we’re building a culture that inspires us to create our life’s work—and creates a bold(er) impact. We know that we’re " +
                "smarter as a group than we are alone. Become one of us if you dare to play bigger.");

    }

    public void step3(){

        seleniumHelper.scrollElement("CAREERS_teams_load_more");
        seleniumHelper.forceClickToElement(seleniumHelper.findElement("CAREERS_teams_load_more"));
        careerActions.findTeam("Quality Assurance");
        seleniumHelper.clickElement("CAREERS_see_allQA_jobs");
        seleniumHelper.assertStringToString(seleniumHelper.getAttribute("CAREERS_filter_department", "title"), "Quality Assurance", "" ,"equals");
        seleniumHelper.clickElement("CAREERS_location_dropdown");
        careerActions.findLocation("Istanbul, Turkey");
        //seleniumHelper.selectDropdown("CAREERS_filter_location", "Istanbul, Turkey");
        seleniumHelper.scrollElement("CAREERS_job_position_location");
    }

    public void step4(){

        seleniumHelper.checkGetTextList("text", "", "inside", "Istanbul, Turkey", "CAREERS_job_position_location");
        seleniumHelper.checkGetTextList("text", "", "inside", "Quality Assurance", "CAREERS_job_position_department");
        seleniumHelper.checkGetTextList("text", "", "inside", "Apply Now", "CAREERS_job_position_apply");
    }

    public void step5(){

        seleniumHelper.hoverElement("CAREERS_job_position_department");
        HashMap<String, String> hashMap =  new HashMap<>();
        hashMap.put("location", seleniumHelper.findElement("CAREERS_job_position_location").getText());
        hashMap.put("department", seleniumHelper.findElement("CAREERS_job_position_department").getText());
        hashMap.put("title", seleniumHelper.findElement("CAREERS_job_position_title").getText());
        seleniumHelper.clickElement("CAREERS_job_position_apply");
        seleniumHelper.switchToTab(1);
        waitHelper.waitUntil("LEVER_position_title", "visible");
        seleniumHelper.checkURLContains("","lever");
        seleniumHelper.assertStringToString(seleniumHelper.findElement("LEVER_position_title").getText(), hashMap.get("title"), "", "equals");
        seleniumHelper.assertStringToString(seleniumHelper.findElement("LEVER_position_categories").getText(), hashMap.get("location").toUpperCase(Locale.ENGLISH), "", "contains");
        seleniumHelper.assertStringToString(seleniumHelper.findElement("LEVER_position_categories").getText(), hashMap.get("department").toUpperCase(Locale.ENGLISH), "", "contains");
    }

}
