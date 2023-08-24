package org.ibs;

import org.ibs.base.BaseTests;
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

        button.click();

        waitting(2);

        Assertions.assertAll(
                ()->Assertions.assertEquals("Добавление товара", titleModuleAddProduct.getText(), "не найдено:добавление товара"),
                ()->Assertions.assertEquals("Наименование", titleModuleName.getText(), "не найдено:наименование"),
                ()->Assertions.assertEquals("Тип", titleModuleType.getText(), "не найден:тип"),
                ()->Assertions.assertEquals("Экзотический", titleModuleExotic.getText(), "не найден: экзотический"),
                ()->Assertions.assertTrue(buttonSave.isDisplayed())
        );


        input.sendKeys("Перец");

        type.click();
        vegetable.click();
        buttonSave.click();

        productList = driver.findElements(By.xpath("//td[1]"));
        typeList = driver.findElements(By.xpath("//td[2]"));
        exotictList = driver.findElements(By.xpath("//td[3]"));

        waitting(5);

        int length = productList.size() - 1;

        Assertions.assertAll(
                ()->Assertions.assertEquals("Перец", productList.get(length).getText(), "не найден:перец"),
                ()->Assertions.assertEquals("Овощ", typeList.get(length).getText(), "не найден:овощ"),
                ()->Assertions.assertEquals("false", exotictList.get(length).getText(), "не найден:false")
        );
    }
}
