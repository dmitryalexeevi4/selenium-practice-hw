package com.github.dmitryalexeevi4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static com.github.dmitryalexeevi4.TestUtils.*;

public class SeleniumHW1 {

    @BeforeClass
    public void webDriverSetup() {
        LOG.info("Настройка ChromeDriver");
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @Test()
    public void buttonPageTest() {
        LOG.info("Выполнение теста страницы Button...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        clickButton("button");
        clickButton("first");
        WebElement expectedResult = webDriver.findElement(By.xpath("//label[text()[contains(.,'Excellent')]]"));
        Assert.assertTrue(expectedResult.isDisplayed());
        WebElement clickMeTooButton = webDriver.findElement(By.className("button-primary"));
        Assert.assertTrue(clickMeTooButton.isDisplayed());
        clickMeTooButton.click();
        assertLink();
    }

    @Test()
    public void checkboxPageTest() {
        LOG.info("Выполнение теста страницы Checkboxes and Radio...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        clickButton("checkbox");
        clickButton("one");
        clickButton("go");
        assertResult("result", "one");
        clickButton("radio_one");
        clickButton("radio_go");
        assertResult("radio_result", "radio_one");
        assertLink();
    }

    @AfterClass
    public void webDriverQuit() {
        LOG.info("Закрытие ChromeDriver");
        webDriver.quit();
    }
}
