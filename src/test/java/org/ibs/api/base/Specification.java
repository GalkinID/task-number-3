package org.ibs.api.base;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.http.Cookies;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import org.ibs.utils.PropManager;

import static io.restassured.RestAssured.*;
import static org.ibs.utils.Param.BASE_URL_API;

public class Specification {
    protected static PropManager propManager = new PropManager();
    private static Cookies cookies = getCookies();

    /**полученик куки(id сессии)
     *
     * @return
     */
    private static Cookies getCookies() {
        return given()
                .baseUri(propManager.getProperty(BASE_URL_API))
                .when()
                .get("/api/food")
                .detailedCookies();
    }

    /**спецификация и настройка запроса
     *
     * @return
     */
    private static RequestSpecification requestSpec() {
        return new RequestSpecBuilder()
                .setBaseUri(propManager.getProperty(BASE_URL_API))
                .setContentType(ContentType.JSON)
                .addCookies(cookies)
                .build();
    }

    /**спецификация проверки и валидация ответа
     *
     * @return
     */
    private static ResponseSpecification responseSpec() {
        return new ResponseSpecBuilder()
                .expectStatusCode(200)
                .build();
    }

    public static void initSpec() {
        requestSpecification = requestSpec();
        responseSpecification = responseSpec();
    }
}
