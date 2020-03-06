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

    @Test(groups = "first_hw")
    public void buttonPageTest() {
        LOG.info("Выполнение теста страницы Button...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        LOG.info("Клик на страницу \"Button\"");
        clickButton("button");

        LOG.info("Клик по кнопке \"Click Me\"");
        clickButton("first");

        LOG.info("Проверка результата на наличие строки \"Excellent\" и кнопки \"Click Me Too\"");
        WebElement expectedResult = webDriver.findElement(By.xpath("//label[text()[contains(.,'Excellent')]]"));
        Assert.assertTrue(expectedResult.isDisplayed());
        WebElement clickMeTooButton = webDriver.findElement(By.className("button-primary"));
        Assert.assertTrue(clickMeTooButton.isDisplayed());

        LOG.info("Клик по кнопке \"Click Me Too\"");
        clickMeTooButton.click();

        LOG.info("Проверка на наличие ссылки для возврата в меню, возврат в меню");
        assertLink();
    }

    @Test(groups = "first_hw")
    public void checkboxPageTest() {
        LOG.info("Выполнение теста страницы Checkboxes and Radio...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(5, TimeUnit.SECONDS);

        LOG.info("Клик на страницу \"Checkboxes and Radio\"");
        clickButton("checkbox");

        LOG.info("Клик на первый чекбокс");
        clickButton("one");

        LOG.info("Клик по кнопке результата");
        clickButton("go");

        LOG.info("Проверка результата на соответствие со значением чекбокса");
        assertResult("result", "one");

        LOG.info("Клик на первую радиокнопку");
        clickButton("radio_one");

        LOG.info("Клик по кнопке результата");
        clickButton("radio_go");

        LOG.info("Проверка результата на соответствие со значением радиокнопки");
        assertResult("radio_result", "radio_one");

        LOG.info("Проверка на наличие ссылки для возврата в меню, возврат в меню");
        assertLink();
    }

    @AfterClass
    public void webDriverQuit() {
        LOG.info("Закрытие ChromeDriver");
        webDriver.quit();
    }

    public WebElement findElementById(String buttonId) {
        return webDriver.findElement(By.id(buttonId));
    }

    public void clickButton(String buttonId) {
        webDriver.findElement(By.id(buttonId)).click();
    }

    public void assertResult(String resultButtonId, String buttonId) {
        WebElement result = findElementById(resultButtonId);
        Assert.assertTrue(result.getText().contains(webDriver.findElement(By.id(buttonId)).getAttribute("value")));
    }

    public void assertLink() {
        WebElement expectedLinkAfterResults = webDriver.findElement(By.xpath("//label//a"));
        Assert.assertTrue(expectedLinkAfterResults.getText().contentEquals("Great! Return to menu"));
        expectedLinkAfterResults.click();
    }
}