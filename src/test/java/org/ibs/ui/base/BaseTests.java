package org.ibs.ui.base;

import io.qameta.allure.Step;
import org.ibs.utils.PropManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.ibs.utils.Param.BASE_URL;

public class BaseTests {
    protected static WebDriver driver;
    protected static WebDriverWait wait;
    protected static PropManager propManager = new PropManager();

    @BeforeAll
    public static void initDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10L));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        driver.get(propManager.getProperty(BASE_URL));
    }

    @AfterAll
    public static void quitDriver() {
        driver.quit();
    }

    protected static void waittingThread(int seconds) {
        try {
            Thread.sleep(seconds*1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
    //--------------методы
    @Step("проверки заполненности таблицы")
    protected void checkTableIsNotEmpty(List<WebElement> number,
                                        List<WebElement> productList,
                                        List<WebElement> typeList,
                                        List<WebElement> exotictList) {
        for (int i = 0; i < number.size(); i++) {
            Assertions.assertTrue(number.get(i).getText().matches("[0-9]+"));
            Assertions.assertTrue(productList.get(i).getText().matches("[а-яА-Яa-zA-Z]+"));
            Assertions.assertTrue(typeList.get(i).getText().matches("Фрукт|Овощ"));
            Assertions.assertTrue(exotictList.get(i).getText().matches("true|false"));
        }
    }

    @Step("проверка отображения кнопки {element}")
    protected void checkElementIsDisplayed(WebElement element) {
        Assertions.assertTrue(element.isDisplayed(), "Элемент не найден");
    }

    @Step("проверка отображения кнопки {element} и нажатие")
    protected void checkIsActiveAndClick(WebElement element) {
        Assertions.assertTrue(element.isDisplayed());
        element.click();
    }

    @Step("Проверка заголовка {expectedText} ")
    protected void checkTitleModalWindow(WebElement element, String expectedText) {
        Assertions.assertEquals(expectedText , element.getText(), String.format("Элемент %s не найден", expectedText));
    }

    @Step("Ввод в поле {element} - '{expectedText}'")
    protected void fillFeid(WebElement element, String expectedText) {
        element.sendKeys(expectedText);
        Assertions.assertEquals(expectedText, element.getAttribute("value"), "Элемент не соотвествует введенному значению");
    }


    @Step("Выбор типа '{expectedText}' из выпадающего списка")
    protected void choiceType(WebElement dropDown, WebElement option, String expectedText) {
        dropDown.click();
        option.click();
        Assertions.assertEquals(expectedText, dropDown.getAttribute("value"),
                "Элемент не соотвествует введенному значению");
    }

    @Step("Установка значения чекбокса - {exotic}")
    protected void choiceCheckbox(WebElement element, String exotic) {
        if(exotic.equals("true")) {
            element.click();
            Assertions.assertTrue(Boolean.parseBoolean(element.getAttribute("checked")),
                    "Элемент не соотвествует введенному значению");
        } else {
            Assertions.assertFalse(Boolean.parseBoolean(element.getAttribute("checked")),
                    "Элемент не соотвествует введенному значению");
        }
    }

    @Step("Проверка добавленого поля - {expectedText} в таблицу")
    protected void checkProductInTable(List<WebElement> elements, String expectedText) {
        for(WebElement element : elements) {
            if(!(element.getText().equals(expectedText))) {
            } else {
                Assertions.assertEquals(element.getText(), expectedText);
                return;
            }
        }
        Assertions.fail("Элемент не найден");
    }

    @Step("ожидание пока элемент - {element} не будет виден")
    protected static WebElement waitting(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element));
    }
}
