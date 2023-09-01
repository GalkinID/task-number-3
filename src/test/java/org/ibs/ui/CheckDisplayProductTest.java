package org.ibs.ui;

import org.ibs.ui.base.BaseTests;
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
        //---- основные элементы страницы
        List<WebElement> number;
        List<WebElement> productList;
        List<WebElement> typeList;
        List<WebElement> exotictList;


        WebElement firstElement = driver.findElement(By.xpath("//tbody/tr"));
        checkElementIsDisplayed(firstElement);

        number = driver.findElements(By.xpath("//th[@scope='row']"));
        productList = driver.findElements(By.xpath("//td[1]"));
        typeList = driver.findElements(By.xpath("//td[2]"));
        exotictList = driver.findElements(By.xpath("//td[3]"));

        checkTableIsNotEmpty(number, productList, typeList, exotictList);

        WebElement button = driver.findElement(By.xpath("//button[@data-target='#editModal']"));
        checkElementIsDisplayed(button);
    }
}
