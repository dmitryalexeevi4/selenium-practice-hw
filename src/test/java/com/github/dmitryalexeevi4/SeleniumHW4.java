package com.github.dmitryalexeevi4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.*;
import java.util.concurrent.TimeUnit;

import static com.github.dmitryalexeevi4.TestUtils.*;

public class SeleniumHW4 {
    @BeforeClass
    public void webDriverSetup() {
        LOG.info("Настройка ChromeDriver");
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @Test()
    public void test() {
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        LOG.info("Выполнение действий на странице Button...");
        clickButton("button");
        clickButton("first");
        WebElement expectedResult = webDriver.findElement(By.xpath("//label[text()[contains(.,'Excellent')]]"));
        Assert.assertTrue(expectedResult.isDisplayed());
        WebElement clickMeTooButton = webDriver.findElement(By.className("button-primary"));
        Assert.assertTrue(clickMeTooButton.isDisplayed());
        clickMeTooButton.click();
        assertLink();

        LOG.info("Выполнение действий на странице Checkboxes and Radio...");
        clickButton("checkbox");
        clickButton("one");
        clickButton("go");
        assertResult("result", "one");
        clickButton("radio_one");
        clickButton("radio_go");
        assertResult("radio_result", "radio_one");
        assertLink();

        LOG.info("Выполнение действий на странице Select...");
        clickButton("select");
        TestUtils.chooseSelectOption("hero");
        TestUtils.chooseSelectMultipleOptions("languages");
        clickButton("go");
        assertSelectedOptionsResult("languages");
        assertLink();

        LOG.info("Выполнение действий на странице Form...");
        clickButton("form");
        fillingReqTextFields();
        clickSubmitButton();
        assertLink();

        LOG.info("Выполнение действий на странице IFrame...");
        clickButton("iframe");
        webDriver.switchTo().frame(0);
        WebElement labelText = webDriver.findElement(By.xpath("//label[@id = 'code']"));
        String text = labelText.getText();
        String code = text.substring(text.lastIndexOf(" ")).trim();
        webDriver.switchTo().parentFrame();
        WebElement textField = webDriver.findElement(By.xpath("//input[@name = 'code']"));
        textField.sendKeys(code);
        clickVerifyButton();
        assertLink();

        LOG.info("Выполнение действий на странице Prompt, Alert and Confirm...");
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

        LOG.info("Выполнение действий на странице Table...");
        clickButton("table");
        chooseMultipleCustomers();
        clickDeleteButton();
        fillingTextFields();
        clickAddButton();
        assertLink();

        List<String> expectedCookies = Arrays.asList("button", "alerts", "checkboxes", "select", "form", "iframe", "table");

        LOG.info("Проверка cookies на значение \"done\"...");
        for (int i = 0; i < expectedCookies.size(); i++) {
            Assert.assertEquals(webDriver.manage().getCookieNamed(expectedCookies.get(i)).getValue(), "done");
        }
        LOG.info("Успешно");
    }

    @AfterClass
    public void webDriverQuit() {
        LOG.info("Закрытие ChromeDriver");
        webDriver.quit();
    }
}
