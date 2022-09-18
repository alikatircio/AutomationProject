package com.test.project.runner;

import com.test.project.scenarios.ScenarioSearchJob;
import com.test.project.utilites.helper.DriverManager;
import com.test.project.utilites.helper.SeleniumHelper;
import com.test.project.utilites.helper.WaitHelper;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.*;

public class Runner {

    private ScenarioSearchJob scenarioSearchJob;
    SeleniumHelper seleniumHelper;
    WaitHelper waitHelper;

    @BeforeTest
    @Parameters({"browser"})
    public void beforeTest(String browser) {

        DriverManager.createDriver(browser);
        WebDriver driver = DriverManager.getDriver();
        this.seleniumHelper = new SeleniumHelper(driver);
        this.waitHelper = new WaitHelper(driver);
        this.scenarioSearchJob = new ScenarioSearchJob(driver, seleniumHelper, waitHelper);
        PageFactory.initElements(driver, scenarioSearchJob);
        PageFactory.initElements(driver, seleniumHelper);
    }

    @Test
    public void run() {

        seleniumHelper.clickElement("HOME_acceptAll_cookie");
        scenarioSearchJob.step1();
        scenarioSearchJob.step2();
        scenarioSearchJob.step3();
        scenarioSearchJob.step4();
        scenarioSearchJob.step5();
    }

    @AfterTest
    public void afterTest() {
        DriverManager.stopDriver();
    }
}
