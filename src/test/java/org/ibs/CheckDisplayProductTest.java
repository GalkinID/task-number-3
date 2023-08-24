package org.ibs;

import org.ibs.base.BaseTests;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.List;

public class CheckDisplayProductTest extends BaseTests {
    @Test
    @Tag("@2")
    @DisplayName("Валидация отображения товаров в списке")
    public void checkDisplayProductTest() {
        List<WebElement> number;
        List<WebElement> productList;
        List<WebElement> typeList;
        List<WebElement> exotictList;


        //----проверка на присутствие минимум одного товара
        WebElement firstElement = driver.findElement(By.xpath("//tbody/tr"));
        Assertions.assertTrue(firstElement.isDisplayed());

        number = driver.findElements(By.xpath("//th[@scope='row']"));
        productList = driver.findElements(By.xpath("//td[1]"));
        typeList = driver.findElements(By.xpath("//td[2]"));
        exotictList = driver.findElements(By.xpath("//td[3]"));

        for (int i = 0; i < number.size(); i++) {
            Assertions.assertTrue(number.get(i).getText().matches("[0-9]+"));
            Assertions.assertTrue(productList.get(i).getText().matches("[а-яА-Яa-zA-Z]+"));
            Assertions.assertTrue(typeList.get(i).getText().matches("Фрукт|Овощ"));
            Assertions.assertTrue(exotictList.get(i).getText().matches("true|false"));
        }

        WebElement button = driver.findElement(By.xpath("//button[@data-target='#editModal']"));
        Assertions.assertTrue(button.isDisplayed());

    }
}
