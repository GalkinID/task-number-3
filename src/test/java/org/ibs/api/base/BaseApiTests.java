package org.ibs.api.base;

import io.qameta.allure.Step;
import org.h2.jdbcx.JdbcDataSource;
import org.ibs.utils.Product;
import org.ibs.utils.PropManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.ibs.api.base.Specification.initSpec;
import static org.ibs.utils.Param.*;

public class BaseApiTests {
    public static PropManager propManager = new PropManager();
    protected static JdbcDataSource dataSource;

    @BeforeAll
    public static void before() {
        initSpec();
        dataSource = new JdbcDataSource();
        dataSource.setURL(propManager.getProperty(DB_URL));
        dataSource.setUser(propManager.getProperty(DB_LOGIN));
        dataSource.setPassword(propManager.getProperty(DB_PASS));
    }

    @Step("отправка GET запроса")
    protected List<Product> sendGet() {
        return given()
                .when()
                .get("/api/food")
                .then()
                .extract()
                .response()
                .jsonPath()
                .getList(".", Product.class);
    }

    @Step("отправка POST запроса с телом {body}")
    protected void sendPost(String body) {
        given()
                .body(body)
                .when()
                .post("/api/food");
    }

    @Step("проверка добавления записи с параметрами,* в базу данных с помощью запроса")
    protected void checkTableWithJDBC(String sql, String name, String type, String exotic) {
        List<Product> products = getProductsFromJDBC(sql);
        checkRowInTable(products, name, type, exotic);
    }

    @Step("проверка отсуствия строки, через базу данных с помощью запроса")
    protected void checkNotInTableWithJDBC(String sql, String name, String type, String exotic) {
        List<Product> products = getProductsFromJDBC(sql);
        checkNotRowInTable(products, name, type, exotic);
    }

    @Step("Проверка присутствия строки в таблице")
    protected void checkRowInTable(List<Product> products, String name, String type, String exotic) {
        boolean boo = false;
        for (Product item : products) {
            if (item.getName().equals(name)
                    && item.getType().equals(type)
                    && item.getExotic().toString().equals(exotic)) {
                boo = true;
            }
        }
        Assertions.assertTrue(boo, "элемент не найден");
    }

    @Step("Проверка отсуствия строки в таблице")
    protected void checkNotRowInTable(List<Product> products, String name, String type, String exotic) {
        boolean boo = false;
        for (Product item : products) {
            if (item.getName().equals(name)
                    && item.getType().equals(type)
                    && item.getExotic().toString().equals(exotic)) {
                boo = true;
            }
        }
        Assertions.assertFalse(boo, "элемент найден");
    }

    @Step("Получение списка продуктов через JDBC")
    protected List<Product> getProductsFromJDBC(String sql) {
        List<Product> products = new ArrayList<>();
        try (Connection connect = dataSource.getConnection()) {
            try (Statement statement = connect.createStatement()) {
                try (ResultSet resultSet = statement.executeQuery(sql)) {
                    while (resultSet.next()) {
                        products.add(new Product<>(
                                resultSet.getString("FOOD_NAME"),
                                resultSet.getString("FOOD_TYPE"),
                                resultSet.getString("FOOD_EXOTIC")
                        ));
                    }
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
        return products;
    }

    @Step("удаление с помощью JDBC")
    protected void deleteWithJDBC(String sql, String name) {
        try (Connection connection = dataSource.getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement(sql)) {
                statement.setString(1, name);
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            throw new RuntimeException();
        }
    }

    @Step("проверка, что таблица не пустая")
    protected void checkTableNotEmpty(List<Product> products) {
        Assertions.assertFalse(products.isEmpty(), "Таблица пустая");
    }
}
