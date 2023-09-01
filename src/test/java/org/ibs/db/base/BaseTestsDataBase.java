package org.ibs.db.base;

import io.qameta.allure.Step;
import org.h2.jdbcx.JdbcDataSource;
import org.ibs.utils.PropManager;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.ibs.utils.Param.*;

public class BaseTestsDataBase {
    protected static JdbcDataSource dataSource;
    protected static Connection connect;

    static PropManager propManager = new PropManager();

    @BeforeAll
    protected static void beforeAll() {
        dataSource = new JdbcDataSource();
        dataSource.setURL(propManager.getProperty(DB_URL));
        dataSource.setUser(propManager.getProperty(DB_LOGIN));
        dataSource.setPassword(propManager.getProperty(DB_PASS));
        try {
            connect = dataSource.getConnection();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @AfterAll
    protected static void afterAll() {
        try {
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    /**Выполнение запроса и получение ответа, преобразование ответа,
     * проверка что записей 4 штуки
     * @param sqlQuery sql запрос
     */
    @Step("Отправка sql запроса {sqlQuery} и преобразование ответа")
    protected void sendQueryAndCheck(String sqlQuery) {
        List<Product> products = new ArrayList<>();
        try (Statement statement = connect.createStatement()) {
            try (ResultSet res = statement.executeQuery(sqlQuery)) {
                while (res.next()) {
                    products.add(new Product(
                            res.getString("FOOD_NAME"),
                            res.getString("FOOD_TYPE"),
                            res.getString("FOOD_EXOTIC")
                    ));
                }
                Assertions.assertTrue(products.size() == 4, "В таюлице не 4 записи");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /** Добавить запись в базу данных с помощью запроса
     *
     * @param sqlQuery sql запрос
     */
    @Step("Добавить запись в базу данных с помощью запроса {sqlQuery}")

    protected void sendInsertQuery(String sqlQuery) {
        try (Statement statement = connect.createStatement()) {
            int res = statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**Проверить добавление записи с параметрами,
     * в базу данных с помощью запроса "SELECT * FROM FOOD"
     *
     * @param sqlQuery
     * @param name имя
     * @param type тип
     * @param exotic экзотический
     */
    @Step("Проверить добавление записи с параметрами,* в базу данных с помощью запроса {sqlQuery}")
    protected void sendQueryAndCheckProduct(String sqlQuery, String name, String type, String exotic) {

        List<Product> products = new ArrayList<>();
        boolean boo = false;

        try (Statement statement = connect.createStatement()) {
            try (ResultSet res = statement.executeQuery(sqlQuery)) {
                while (res.next()) {
                    products.add(new Product(
                            res.getString("FOOD_NAME"),
                            res.getString("FOOD_TYPE"),
                            res.getString("FOOD_EXOTIC")
                    ));
                }

                for (Product product : products) {
                    if(product.getName().equals(name) &&
                            product.getType().equals(type) &&
                            product.getIsExotic().equals(exotic)) {
                        boo = true;
                    }
                }
                Assertions.assertTrue(boo, "элемент не найден");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    /**  Удалить добавленную запись в базе данных
     *
     * @param sqlQuery sql запрос
     */

    protected void deleteQueryProduct(String sqlQuery) {
        try (Statement statement = connect.createStatement()) {
            int res = statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    //реализация через  preparedStatement

    /** Добавить запись в базу данных с помощью запроса
     *
     * @param sqlQuery sql запрос
     */
    @Step("Добавить запись в базу данных с помощью запроса {sqlQuery}")
    protected void sendInsertQueryWithPrepareStat(String sqlQuery, int id, String name, String type, String exotic) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery)) {
            preparedStatement.setInt(1, id);
            preparedStatement.setString(2, name);
            preparedStatement.setString(3, type);
            preparedStatement.setString(4, exotic);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**  Удаление добавленной записи в базе данных
     *
     * @param sqlQuery sql запрос
     */
    @Step("Удаление добавленной записи в базе данных {sqlQuery}")
    protected void deleteQueryProductWithPrepareStat(String sqlQuery, String name) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1,name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
