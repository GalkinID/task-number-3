package org.ibs.api;

import org.ibs.api.base.BaseApiTests;
import org.ibs.utils.Product;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.ibs.utils.Query.*;

public class CheckProductTableAPITest extends BaseApiTests {
    @Test
    @Tag("@1")
    @DisplayName("Добавление нового товара")
    public void addProductTest() {
        List<Product> products = sendGet();
        checkNotRowInTable(products, "Перец", "VEGETABLE", "false");
        checkNotInTableWithJDBC(SELECT, "Перец", "VEGETABLE", "0");

        sendPost(POST_BODY);

        List<Product> afterPost = sendGet();
        checkRowInTable(afterPost, "Перец", "VEGETABLE", "false");
        checkTableWithJDBC(SELECT, "Перец", "VEGETABLE", "0");

        deleteWithJDBC(DELETE, "Перец");
    }

    @Test
    @Tag("@2")
    @DisplayName("проверка, что таблица не пустая")
    public void checkProductTableTest() {
        List<Product> products = sendGet();
        checkTableNotEmpty(products);

        //эксперимент
        checkRowInTable(products, "Апельсин", "FRUIT", "true");
    }



}