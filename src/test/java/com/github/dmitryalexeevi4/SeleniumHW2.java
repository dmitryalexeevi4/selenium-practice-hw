package com.github.dmitryalexeevi4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

import static com.github.dmitryalexeevi4.TestUtils.*;

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
        clickButton("select");
        TestUtils.chooseSelectOption("hero");
        TestUtils.chooseSelectMultipleOptions("languages");
        clickButton("go");
        assertSelectedOptionsResult("languages");
        assertLink();
    }

    @Test()
    public void formPageTest() {
        LOG.info("Выполнение теста страницы Form...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        clickButton("form");
        fillingReqTextFields();
        clickSubmitButton();
        assertLink();
    }

    @Test()
    public void iframePageTest() {
        LOG.info("Выполнение теста страницы IFrame...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
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
    }

    @AfterClass
    public void webDriverQuit() {
        LOG.info("Закрытие ChromeDriver");
        webDriver.quit();
    }
}
