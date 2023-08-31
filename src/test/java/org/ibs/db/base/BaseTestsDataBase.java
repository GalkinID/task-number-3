package org.ibs.db.base;

import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

import static org.ibs.utils.DBParam.*;

public class BaseTestsDataBase {
    protected static JdbcDataSource dataSource;
    protected static Connection connect;
    protected static Properties properties = new Properties();

    @BeforeAll
    protected static void beforeAll() {
        try {
            properties.load(new FileInputStream(new File("src/test/resources/dbproperties.properties")));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        dataSource = new JdbcDataSource();
        dataSource.setURL(properties.getProperty(DB_URL));
        dataSource.setUser(properties.getProperty(DB_LOGIN));
        dataSource.setPassword(properties.getProperty(DB_PASS));
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
    protected void eleteQueryProduct(String sqlQuery) {
        try (Statement statement = connect.createStatement()) {
            int res = statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
