package org.ibs.utils;

public class Query {
    public static final String SELECT = "SELECT * FROM FOOD";
    public static final String INSERT = "INSERT INTO FOOD VALUES(?, ?, ?, ?)";
    public static final String DELETE = "DELETE FROM FOOD WHERE FOOD_NAME = ?";
    public static final String POST_BODY= "{\"name\": \"Перец\", \"type\": \"VEGETABLE\", \"exotic\": false}";
}
