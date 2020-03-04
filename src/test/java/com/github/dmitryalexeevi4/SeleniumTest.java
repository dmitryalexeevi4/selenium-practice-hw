package com.github.dmitryalexeevi4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.slf4j.*;
import org.testng.Assert;
import org.testng.annotations.*;

import java.util.concurrent.TimeUnit;

public class SeleniumTest {
    private static final Logger LOG = LoggerFactory.getLogger(SeleniumTest.class);
    private static WebDriver webDriver;

    @BeforeClass
    public void webDriverSetup() {
        LOG.info("Настройка ChromeDriver");
        WebDriverManager.chromedriver().setup();
        webDriver = new ChromeDriver();
    }

    @Test
    public void webDriverTest() {
        LOG.info("Выполнение теста");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);
        webDriver.get("https://savkk.github.io/selenium-practice/");
        WebElement button = webDriver.findElement(By.id("button"));
        button.click();
        WebElement clickMeButton = webDriver.findElement(By.id("first"));
        clickMeButton.click();

        WebElement expectedResult = webDriver.findElement(By.xpath("//label[text()[contains(.,'Excellent')]]"));
        Assert.assertTrue(expectedResult.isDisplayed());

        WebElement clickMeTooButton = webDriver.findElement(By.className("button-primary"));
        Assert.assertTrue(clickMeTooButton.isDisplayed());
        clickMeTooButton.click();

        WebElement expectedLink = webDriver.findElement(By.xpath("//label//a"));
        Assert.assertTrue(expectedLink.getText().contentEquals("Great! Return to menu"));
        expectedLink.click();

        WebElement checkboxAndRadio = webDriver.findElement(By.id("checkbox"));
        checkboxAndRadio.click();

        WebElement checkbox = webDriver.findElement(By.id("one"));
        checkbox.click();
        WebElement getCheckboxResultsButton = webDriver.findElement(By.id("go"));
        getCheckboxResultsButton.click();
        WebElement checkboxResult = webDriver.findElement(By.id("result"));
        Assert.assertTrue(checkboxResult.getText().contains(checkbox.getAttribute("value")));

        WebElement radioButton = webDriver.findElement(By.id("radio_one"));
        radioButton.click();
        WebElement getRadioResultsButton = webDriver.findElement(By.id("radio_go"));
        getRadioResultsButton.click();
        WebElement radioResult = webDriver.findElement(By.id("radio_result"));
        Assert.assertTrue(radioResult.getText().contains(radioButton.getAttribute("value")));

        WebElement expectedLinkAfterResults = webDriver.findElement(By.xpath("//label//a"));
        Assert.assertTrue(expectedLinkAfterResults.getText().contentEquals("Great! Return to menu"));
        expectedLinkAfterResults.click();

    }

    @AfterClass
    public void webDriverShutDown() {
        LOG.info("Закрытие ChromeDriver");
        webDriver.quit();
    }
}
