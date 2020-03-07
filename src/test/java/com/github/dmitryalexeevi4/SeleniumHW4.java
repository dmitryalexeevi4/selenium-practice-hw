package com.github.dmitryalexeevi4;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.annotations.*;

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
    public void dragAndDropPageTest() {
        LOG.info("Выполнение теста страницы Drag'n'Drop...");
        webDriver.get("https://savkk.github.io/selenium-practice/");
        webDriver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

        clickButton("dnd");
        WebElement firstBlackSquare = webDriver.findElement(By.xpath("//div[@id = 'content']//div[1]"));
        LOG.info(firstBlackSquare.getAttribute("class"));
        WebElement redSquare = webDriver.findElement(By.xpath("//div[@id = 'content']//div[2]"));
        LOG.info(redSquare.getAttribute("class"));
        WebElement secBlackSquare = webDriver.findElement(By.xpath("//div[@id = 'content']//div[3]"));
        LOG.info(secBlackSquare.getAttribute("class"));

        Actions builder = new Actions(webDriver);

        builder.dragAndDrop(redSquare, firstBlackSquare).perform();
        builder.dragAndDrop(redSquare, secBlackSquare).perform();
    }

    @AfterClass
    public void webDriverQuit() {
        LOG.info("Закрытие ChromeDriver");
        //webDriver.quit();
    }
}
