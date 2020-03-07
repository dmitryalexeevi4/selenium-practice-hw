package com.github.dmitryalexeevi4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static com.github.dmitryalexeevi4.TestUtils.LOG;
import static com.github.dmitryalexeevi4.TestUtils.webDriver;

public class SeleniumHW2 {

    @BeforeClass
    public void webDriverSetup() {
        LOG.info("Настройка ChromeDriver");
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @Test()
    public void selectPageTest() {
        LOG.info("Выполнение теста страницы Select...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        TestUtils.clickButton("select");
        TestUtils.chooseSelectOption("hero");
        TestUtils.chooseSelectMultipleOptions("languages");
        TestUtils.clickButton("go");
        TestUtils.assertSelectedOptionsResult("languages");
        TestUtils.assertLink();
    }

    @Test()
    public void formPageTest() {
        LOG.info("Выполнение теста страницы Form...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        TestUtils.clickButton("form");
        TestUtils.fillingReqTextFields();
        TestUtils.clickSubmitButton();
        TestUtils.assertLink();
    }

    @Test()
    public void iframePageTest() {
        LOG.info("Выполнение теста страницы IFrame...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        TestUtils.clickButton("iframe");
        webDriver.switchTo().frame(0);
        WebElement labelText = webDriver.findElement(By.xpath("//label[@id = 'code']"));
        String text = labelText.getText();
        String code = text.substring(text.lastIndexOf(" ")).trim();
        webDriver.switchTo().parentFrame();
        WebElement textField = webDriver.findElement(By.xpath("//input[@name = 'code']"));
        textField.sendKeys(code);
        TestUtils.clickVerifyButton();
        TestUtils.assertLink();
    }

    @AfterClass
    public void webDriverQuit() {
        LOG.info("Закрытие ChromeDriver");
        webDriver.quit();
    }
}
