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

        button.click();
        waitting(titleModuleAddProduct);

        Assertions.assertAll(
                ()->Assertions.assertEquals("Добавление товара", titleModuleAddProduct.getText(), "не найдено:добавление товара"),
                ()->Assertions.assertEquals("Наименование", titleModuleName.getText(), "не найдено:наименование"),
                ()->Assertions.assertEquals("Тип", titleModuleType.getText(), "не найден:тип"),
                ()->Assertions.assertEquals("Экзотический", titleModuleExotic.getText(), "не найден: экзотический"),
                ()->Assertions.assertTrue(buttonSave.isDisplayed())
        );

        input.sendKeys("Перец");
        Assertions.assertEquals("Перец", input.getAttribute("value"), "Элемент не соотвествует введенному значению");

        type.click();
        vegetable.click();
        Assertions.assertEquals("VEGETABLE", type.getAttribute("value"), "Элемент не соотвествует введенному значению");

        WebElement checkBox = driver.findElement(By.xpath("//input[@id='exotic']"));
        Assertions.assertFalse(Boolean.parseBoolean(checkBox.getAttribute("checked")), "Элемент не соотвествует введенному значению");

        buttonSave.click();

        productList = driver.findElements(By.xpath("//td[1]"));
        typeList = driver.findElements(By.xpath("//td[2]"));
        exotictList = driver.findElements(By.xpath("//td[3]"));


        WebElement modal = driver.findElement(By.xpath("//div[@id='editModal']"));
        System.out.println(modal.getAttribute("style"));

        for (int i = 0; i < 3; i++) {
            if (modal.getAttribute("style").contains("none")) {
                int length = productList.size() - 1;

                Assertions.assertAll(
                        ()->Assertions.assertEquals("Перец", productList.get(length).getText(), "не найден:перец"),
                        ()->Assertions.assertEquals("Овощ", typeList.get(length).getText(), "не найден:овощ"),
                        ()->Assertions.assertEquals("false", exotictList.get(length).getText(), "не найден:false")
                );
            } else {
                waittingThread(1);
            }
        }
    }
}