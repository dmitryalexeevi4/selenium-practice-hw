package com.github.dmitryalexeevi4;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.Select;
import org.slf4j.*;
import org.testng.Assert;

import java.util.List;

class TestUtils {
    static final Logger LOG = LoggerFactory.getLogger(TestUtils.class);
    static WebDriver webDriver;

    static WebElement findElementById(String buttonId) {
        return webDriver.findElement(By.id(buttonId));
    }

    static void clickButton(String buttonId) {
        webDriver.findElement(By.id(buttonId)).click();
    }

    static void clickSubmitButton() {
        webDriver.findElement(By.xpath("//input[@type = 'submit']")).click();
    }

    static void clickVerifyButton() {
        webDriver.findElement(By.xpath("//input[@value = 'Verify']")).click();
    }

    static void clickDeleteButton() {
        webDriver.findElement(By.xpath("//input[@value = 'Delete']")).click();
    }

    static void clickAddButton() {
        webDriver.findElement(By.xpath("//input[@value = 'Add']")).click();
    }

    static void assertResult(String resultButtonId, String buttonId) {
        WebElement result = findElementById(resultButtonId);
        Assert.assertTrue(result.getText().contains(webDriver.findElement(By.id(buttonId)).getAttribute("value")));
    }

    static void assertLink() {
        WebElement expectedLinkAfterResults = webDriver.findElement(By.xpath("//label//a"));
        Assert.assertTrue(expectedLinkAfterResults.getText().contentEquals("Great! Return to menu"));
        expectedLinkAfterResults.click();
    }

    static void assertTextAndLink() {
        WebElement expectedText = webDriver.findElement(By.xpath("//label[.='Great!']"));
        Assert.assertTrue(expectedText.isDisplayed());

        WebElement expectedLink = webDriver.findElement(By.className("return"));
        Assert.assertTrue(expectedLink.isDisplayed());
        expectedLink.click();
    }

    static void chooseSelectOption(String selectName) {
        WebElement selectElement = webDriver.findElement(By.name(selectName));
        Select select = new Select(selectElement);
        select.selectByIndex(1);
    }

    static void chooseSelectMultipleOptions(String selectName) {
        WebElement selectElement = webDriver.findElement(By.name(selectName));
        Select select = new Select(selectElement);
        for (int index = 0; index <= 2; index++) {
            select.selectByIndex(index);
        }
    }

    static void chooseMultipleCustomers() {
        List<WebElement> checkboxes = webDriver.findElements(By.xpath("//table[@id = 'customers']//tr//td//input[@type = 'checkbox']"));
        for (WebElement checkbox : checkboxes) {
            checkbox.click();
        }
    }

    static void assertSelectedOptionsResult(String selectName) {
        WebElement selectElement = webDriver.findElement(By.name(selectName));
        Select select = new Select(selectElement);
        List<WebElement> selectedOptions = select.getAllSelectedOptions();
        String result = selectedOptions.get(0).getText() + ", " + selectedOptions.get(1).getText() + ", " + selectedOptions.get(2).getText();
        WebElement expectedResult = webDriver.findElement(By.xpath("//label[@name ='result'][text()[contains(., ', ')]]"));
        Assert.assertEquals(result, expectedResult.getText());
    }

    static void fillingTextFields() {
        List<WebElement> textFields = webDriver.findElements(By.xpath("//form//input[@type='text']"));
        for (WebElement textField : textFields) {
            textField.sendKeys("test");
        }
    }

    static void fillingReqTextFields() {
        List<WebElement> textFields = webDriver.findElements(By.xpath("//form//input[@type='text'][@required]"));
        for (WebElement textField : textFields) {
            textField.sendKeys("test");
        }

        WebElement emailField = webDriver.findElement(By.xpath("//form//input[@type='email'][@required]"));
        emailField.sendKeys("test@email.com");

        WebElement uploadFile = webDriver.findElement(By.xpath("//form//input[@type='file'][@required]"));
        uploadFile.sendKeys("C:\\Users\\Роман\\IdeaProjects\\selenium-practice-hw\\src\\test\\resources");

        WebElement textareaField = webDriver.findElement(By.xpath("//form//textarea[@required]"));
        textareaField.sendKeys("test text");
    }
}
