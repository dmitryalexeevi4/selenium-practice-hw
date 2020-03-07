package com.github.dmitryalexeevi4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static com.github.dmitryalexeevi4.TestUtils.*;

public class SeleniumHW3 {
    @BeforeClass
    public void webDriverSetup() {
        LOG.info("Настройка ChromeDriver");
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @Test()
    public void promptPageTest() {
        LOG.info("Выполнение теста страницы Prompt, Alert and Confirm...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        clickButton("alerts");

        webDriver.findElement(By.className("get")).click();
        Alert getPassAlert = webDriver.switchTo().alert();
        String alertMessage = getPassAlert.getText();
        LOG.info(alertMessage);

        String password = alertMessage.substring(alertMessage.lastIndexOf(" ")).trim();
        LOG.info(password);

        getPassAlert.accept();

        webDriver.findElement(By.className("set")).click();
        Alert setPassAlert = webDriver.switchTo().alert();
        setPassAlert.sendKeys(password);
        setPassAlert.accept();

        assertTextAndLink();

        Alert areYouSureAlert = webDriver.switchTo().alert();
        areYouSureAlert.accept();
    }

    @Test()
    public void tablePageTest() {
        LOG.info("Выполнение теста страницы Table...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        clickButton("table");
        chooseMultipleCustomers();
        clickDeleteButton();

        fillingTextFields();
        clickAddButton();
        assertLink();
    }

    @AfterClass
    public void webDriverQuit() {
        LOG.info("Закрытие ChromeDriver");
        webDriver.quit();
    }
}
