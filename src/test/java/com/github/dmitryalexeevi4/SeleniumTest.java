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

    @Test(priority = 1)
    public void buttonPageTest() {
        LOG.info("Выполнение теста страницы Button...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        LOG.info("Клик на страницу \"Button\"");
        WebElement button = webDriver.findElement(By.id("button"));
        button.click();

        LOG.info("Клик по кнопке \"Click Me\"");
        WebElement clickMeButton = webDriver.findElement(By.id("first"));
        clickMeButton.click();

        LOG.info("Проверка результата на наличие строки \"Excellent\" и кнопки \"Click Me Too\"");
        WebElement expectedResult = webDriver.findElement(By.xpath("//label[text()[contains(.,'Excellent')]]"));
        Assert.assertTrue(expectedResult.isDisplayed());
        WebElement clickMeTooButton = webDriver.findElement(By.className("button-primary"));
        Assert.assertTrue(clickMeTooButton.isDisplayed());

        LOG.info("Клик по кнопке \"Click Me Too\"");
        clickMeTooButton.click();

        LOG.info("Проверка на наличие ссылки для возврата в меню");
        WebElement expectedLink = webDriver.findElement(By.xpath("//label//a"));
        Assert.assertTrue(expectedLink.getText().contentEquals("Great! Return to menu"));

        LOG.info("Клик по ссылке");
        expectedLink.click();
    }

    @Test(priority = 2)
    public void checkboxPageTest() {
        LOG.info("Выполнение теста страницы Checkboxes and Radio...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        LOG.info("Клик на страницу \"Checkboxes and Radio\"");
        WebElement checkboxAndRadio = webDriver.findElement(By.id("checkbox"));
        checkboxAndRadio.click();

        LOG.info("Клик на первый чекбокс");
        WebElement checkbox = webDriver.findElement(By.id("one"));
        checkbox.click();

        LOG.info("Клик по кнопке результата");
        WebElement getCheckboxResultsButton = webDriver.findElement(By.id("go"));
        getCheckboxResultsButton.click();

        LOG.info("Проверка результата на соответствие со значением чекбокса");
        WebElement checkboxResult = webDriver.findElement(By.id("result"));
        Assert.assertTrue(checkboxResult.getText().contains(checkbox.getAttribute("value")));

        LOG.info("Клик на первую радиокнопку");
        WebElement radioButton = webDriver.findElement(By.id("radio_one"));
        radioButton.click();

        LOG.info("Клик по кнопке результата");
        WebElement getRadioResultsButton = webDriver.findElement(By.id("radio_go"));
        getRadioResultsButton.click();

        LOG.info("Проверка результата на соответствие со значением радиокнопки");
        WebElement radioResult = webDriver.findElement(By.id("radio_result"));
        Assert.assertTrue(radioResult.getText().contains(radioButton.getAttribute("value")));

        LOG.info("Проверка на наличие ссылки для возврата в меню");
        WebElement expectedLinkAfterResults = webDriver.findElement(By.xpath("//label//a"));
        Assert.assertTrue(expectedLinkAfterResults.getText().contentEquals("Great! Return to menu"));

        LOG.info("Клик по ссылке");
        expectedLinkAfterResults.click();
    }

    @AfterClass
    public void webDriverQuit() {
        LOG.info("Закрытие ChromeDriver");
        webDriver.quit();
    }
}
