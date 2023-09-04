package org.ibs.cucumber.dbsteps;

import io.cucumber.java.ru.И;

import static org.ibs.db.base.BaseTestsDataBase.*;

public class CucumberDBSteps {
    @И("соединение с БД")
    public void connectDataBaseCucumber() {
        connectDataBase();
    }
    @И("закрытие соединения с БД")
    public void closeConnectDataBaseCucumber() {
        closeConnectDataBase();
    }
    @И("^выполнение запроса '(.+)' и получение всех записей$")
    public void sendQueryAndCheckCucumber(String sqlQuery) {
        sendQueryAndCheck(sqlQuery);
    }
    @И("^проверка добавленной записи с параметрами наименование - '(.+)', тип - '(.+)', экзотический - '(.+)', с помощью запроса '(.+)'$")
    public void sendQueryAndCheckProductCucumber(String name, String type, String exotic, String sqlQuery) {
        sendQueryAndCheckProduct(name, type, exotic, sqlQuery);
    }
    @И("^добавить запись в базу данных с помощью запроса '(.+)', '(.+)', '(.+)', '(.+)', '(.+)'$")
    public void sendInsertQueryCucumber(String sqlQuery, int id, String name, String type, String exotic) {
        sendInsertQuery(sqlQuery, id, name, type, exotic);
    }
    @И("^удаление добавленной записи с параметром '(.+)', с помощью запроса '(.+)'$")
    public void deleteQueryProductCucumber(String name, String sqlQuery) {
        deleteQueryProduct(name, sqlQuery);
    }
}
