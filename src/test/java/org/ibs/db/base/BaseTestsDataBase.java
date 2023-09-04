package org.ibs.db.base;

import org.h2.jdbcx.JdbcDataSource;
import org.ibs.utils.PropManager;
import org.junit.jupiter.api.Assertions;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static org.ibs.utils.Param.*;

public class BaseTestsDataBase {
    protected static JdbcDataSource dataSource;
    protected static Connection connect;
    static PropManager propManager = new PropManager();

    public static void connectDataBase() {
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

    public static void closeConnectDataBase() {
        try {
            connect.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public static void sendQueryAndCheck(String sqlQuery) {
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
                Assertions.assertTrue(products.size() > 0, "Таблица пустая");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendInsertQuery(String sqlQuery) {
        try (Statement statement = connect.createStatement()) {
            int res = statement.executeUpdate(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void sendQueryAndCheckProduct(String name, String type, String exotic, String sqlQuery) {

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
                    if (product.getName().equals(name) &&
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

    public static void sendInsertQuery(String sqlQuery, int id, String name, String type, String exotic) {
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

    public static void deleteQueryProduct(String name, String sqlQuery) {
        try (PreparedStatement preparedStatement = connect.prepareStatement(sqlQuery)) {
            preparedStatement.setString(1, name);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
