package org.ibs.db;

import org.ibs.db.base.BaseTestsDataBase;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static org.ibs.utils.Query.*;

public class AddProductDateBaseTest extends BaseTestsDataBase {
    @Test
    @Tag("@1")
    @DisplayName("Добавление нового товара через базу данных")
    public void addProductDataBase() {
        sendQueryAndCheck(SELECT);
        sendInsertQueryWithPrepareStat(INSERT, 5, "Перец", "VEGETABLE", "0");
        sendQueryAndCheckProduct(SELECT, "Перец", "VEGETABLE", "0");
        deleteQueryProductWithPrepareStat(DELETE, "Перец");
        sendQueryAndCheck(SELECT);
    }
}

