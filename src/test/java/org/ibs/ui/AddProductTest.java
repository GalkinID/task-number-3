package org.ibs.ui;

import org.ibs.ui.base.BaseTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class AddProductTest extends BaseTests {

    @Test
    @Tag("@1")
    @DisplayName("Добавление нового товара")
    public void addProductTest() {

        //---- элементы основной страницы
        WebElement button = driver.findElement(By.xpath("//button[@data-target='#editModal']"));
        List<WebElement> productList;
        List<WebElement> typeList;
        List<WebElement> exotictList;

        //---- элементы для проверки модального окна
        WebElement titleModuleAddProduct = driver.findElement(By.xpath("//h5[@id='editModalLabel']"));
        WebElement titleModuleName = driver.findElement(By.xpath("//label[@for='name']"));
        WebElement titleModuleType = driver.findElement(By.xpath("//label[@for='type']"));
        WebElement titleModuleExotic = driver.findElement(By.xpath("//label[@for='exotic']"));
        WebElement buttonSave = driver.findElement(By.xpath("//button[@id='save']"));

        //---- элементы для ввода данных в модальное окно
        WebElement input = driver.findElement(By.xpath("//input[@class='form-control']"));
        WebElement type = driver.findElement(By.xpath("//select[@id='type']"));
        WebElement vegetable = driver.findElement(By.xpath("//option[@value='VEGETABLE']"));

        checkIsActiveAndClick(button);

        waitting(titleModuleAddProduct);

        checkTitleModalWindow(titleModuleAddProduct, "Добавление товара");
        checkTitleModalWindow(titleModuleName, "Наименование");
        checkTitleModalWindow(titleModuleType, "Тип");
        checkTitleModalWindow(titleModuleExotic, "Экзотический");
        checkElementIsDisplayed(buttonSave);

        fillFeid(input, "Перец");

        choiceType(type, vegetable, "VEGETABLE");

        WebElement checkBox = driver.findElement(By.xpath("//input[@id='exotic']"));
        choiceCheckbox(checkBox, "checked");

        checkIsActiveAndClick(buttonSave);

        productList = driver.findElements(By.xpath("//td[1]"));
        typeList = driver.findElements(By.xpath("//td[2]"));
        exotictList = driver.findElements(By.xpath("//td[3]"));

        checkProductInTable(productList, "Перец");
        checkProductInTable(typeList, "Овощ");
        checkProductInTable(exotictList, "false");

    }
}