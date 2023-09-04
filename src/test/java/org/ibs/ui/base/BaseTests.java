package org.ibs.ui.base;

import org.ibs.utils.PropManager;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
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

    //---- элементы основной страницы
    @FindBy(xpath = "//button[@data-target='#editModal']")
    private WebElement button;
    @FindBy(xpath = "//td[1]")
    private List<WebElement> productList;
    @FindBy(xpath = "//td[2]")
    private List<WebElement> typeList;
    @FindBy(xpath = "//td[3]")
    private List<WebElement> exotictList;

    //---- элементы для проверки модального окна
    @FindBy(xpath = "//h5[@id='editModalLabel']")
    private WebElement titleModuleAddProduct;
    @FindBy(xpath = "//label[@for='name']")
    private WebElement titleModuleName;
    @FindBy(xpath = "//label[@for='type']")
    private WebElement titleModuleType;
    @FindBy(xpath = "//label[@for='exotic']")
    private WebElement titleModuleExotic;
    @FindBy(xpath = "//button[@id='save']")
    private WebElement buttonSave;

    //---- элементы для ввода данных в модальное окно
    @FindBy(xpath = "//input[@class='form-control']")
    private WebElement input;
    @FindBy(xpath = "//select[@id='type']")
    private WebElement type;
    @FindBy(xpath = "//option[@value='VEGETABLE']")
    private WebElement vegetable;
    @FindBy(xpath = "//input[@id='exotic']")
    private WebElement checkBox;
    @FindBy(xpath = "//tbody/tr")
    private WebElement firstElement;
    @FindBy(xpath = "//th[@scope='row']")
    private List<WebElement> number;


    public void initDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver.exe");
        driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10L));
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(15, TimeUnit.SECONDS);
        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);

        PageFactory.initElements(driver, this);
        driver.get(propManager.getProperty(BASE_URL));

    }

    public void quitDriver() {
        driver.quit();
    }

    public void waittingThread(int seconds) {
        try {
            Thread.sleep(seconds * 1000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    //--------------методы
    public void checkTableIsNotEmpty() {
        for (int i = 0; i < number.size(); i++) {
            Assertions.assertTrue(number.get(i).getText().matches("[0-9]+"));
            Assertions.assertTrue(productList.get(i).getText().matches("[а-яА-Яa-zA-Z]+"));
            Assertions.assertTrue(typeList.get(i).getText().matches("Фрукт|Овощ"));
            Assertions.assertTrue(exotictList.get(i).getText().matches("true|false"));
        }
    }

    public void checkButtonSaveIsDisplayed() {
        Assertions.assertTrue(buttonSave.isDisplayed(), "Элемент не найден");
    }
    public void checkFirstElementIsDisplayed() {
        Assertions.assertTrue(firstElement.isDisplayed(), "Элемент не найден");
    }
    public void checkButtonAdd() {
        Assertions.assertTrue(button.isDisplayed(), "Элемент не найден");
    }

    public void checkIsActiveAndClick() {
        Assertions.assertTrue(button.isDisplayed());
        button.click();
    }
    public void clickButtonSave() {
        buttonSave.click();
    }

    public void checkTitleModalWindow(String title, String name, String type, String exotic) {
        Assertions.assertAll(
                () -> Assertions.assertEquals(title, titleModuleAddProduct.getText(), String.format("Элемент %s не найден", title)),
                () -> Assertions.assertEquals(name, titleModuleName.getText(), String.format("Элемент %s не найден", name)),
                () -> Assertions.assertEquals(type, titleModuleType.getText(), String.format("Элемент %s не найден", type)),
                () -> Assertions.assertEquals(exotic, titleModuleExotic.getText(), String.format("Элемент %s не найден", exotic))
        );
    }


    public void fillFeid(String expectedText) {
        input.sendKeys(expectedText);
        Assertions.assertEquals(expectedText, input.getAttribute("value"), "Элемент не соотвествует введенному значению");
    }


    public void choiceType(String expectedText) {
        type.click();
        vegetable.click();
        Assertions.assertEquals(expectedText, type.getAttribute("value"),
                "Элемент не соотвествует введенному значению");
    }

    public void choiceCheckbox(String exotic) {
        if (exotic.equals("true")) {
            checkBox.click();
            Assertions.assertTrue(Boolean.parseBoolean(checkBox.getAttribute("checked")),
                    "Элемент не соотвествует введенному значению");
        } else {
            Assertions.assertFalse(Boolean.parseBoolean(checkBox.getAttribute("checked")),
                    "Элемент не соотвествует введенному значению");
        }
    }

    public void checkProductForName(String expectedText) {
        for (WebElement element : productList) {
            if (!(element.getText().equals(expectedText))) {
            } else {
                Assertions.assertEquals(element.getText(), expectedText);
                return;
            }
        }
        Assertions.fail("Элемент не найден");
    }
    public void checkProductForType(String expectedText) {
        for (WebElement element : typeList) {
            if (!(element.getText().equals(expectedText))) {
            } else {
                Assertions.assertEquals(element.getText(), expectedText);
                return;
            }
        }
        Assertions.fail("Элемент не найден");
    }
    public void checkProductForExotic(String expectedText) {
        for (WebElement element : exotictList) {
            if (!(element.getText().equals(expectedText))) {
            } else {
                Assertions.assertEquals(element.getText(), expectedText);
                return;
            }
        }
        Assertions.fail("Элемент не найден");
    }

    public WebElement waitModal() {
        return wait.until(ExpectedConditions.visibilityOf(titleModuleAddProduct));
    }
}
