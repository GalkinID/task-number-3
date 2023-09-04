package org.ibs.cucumber.uisteps;

import io.cucumber.java.ru.И;
import org.ibs.ui.base.BaseTests;

public class CucumberUISteps{
    public BaseTests baseTests = new BaseTests();

    @И("инициализация драйвера и открытие браузера")
    public void initDriverCucumber() {
        baseTests.initDriver();
    }
    @И("закрытие окна браузера и драйвера")
    public void quitDriverCucumber() {
        baseTests.quitDriver();
    }
    @И("проверка отображения кнопки и нажатие")
    public void checkIsActiveAndClickCucumber() {
        baseTests.checkIsActiveAndClick();
    }
    @И("ожидание пока элемент - модальное окно не появится")
    public void waitModalCucumber() {
        baseTests.waitModal();
    }
    @И("^проверка заголовков '(.+)', '(.+)', '(.+)', '(.+)'$")
    public void checkTitleModalWindowCucumber(String title, String name, String type, String exotic) {
        baseTests.checkTitleModalWindow(title, name, type, exotic);
    }
    @И("проверка отображения кнопки 'сохранить'")
    public void checkButtonSaveIsDisplayedCucumber() {
        baseTests.checkButtonSaveIsDisplayed();
    }
    @И("^ввод в поле значение '(.+)'$")
    public void fillFeidCucumber(String expectedText) {
        baseTests.fillFeid(expectedText);
    }
    @И("^выбор типа из выпадающего списка '(.+)'$")
    public void choiceTypeCucumber(String expectedText) {
        baseTests.choiceType(expectedText);
    }
    @И("^установка значения чекбокса '(.+)'$")
    public void choiceCheckboxCucumber(String exotic) {
        baseTests.choiceCheckbox(exotic);
    }
    @И("нажатие кнопки 'сохранить'")
    public void clickButtonSaveCucumber() {
        baseTests.clickButtonSave();
    }
    @И("^проверка добавленого поля - в таблицу '(.+)'$")
    public void checkProductForNameCucumber(String expectedText) {
        baseTests.checkProductForName(expectedText);
    }
    @И("^проверка добавленого типа - в таблицу '(.+)'$")
    public void checkProductForTypeCucumber(String expectedText) {
        baseTests.checkProductForType(expectedText);
    }
    @И("^проверка добавленого экзотический - в таблицу '(.+)'$")
    public void checkProductForExoticCucumber(String expectedText) {
        baseTests.checkProductForExotic(expectedText);
    }

    @И("проверка отображения первого элемента")
    public void checkFirstElementIsDisplayedCucumber() {
        baseTests.checkFirstElementIsDisplayed();
    }
    @И("проверка, что таблица не пустая")
    public void checkTableIsNotEmptyCucumber() {
        baseTests.checkTableIsNotEmpty();
    }
    @И("проверка отображения кнопки")
    public void checkButtonAddCucumber() {
        baseTests.checkButtonAdd();
    }
}
