package ru.vsk;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;


import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;

public class RegisterTest {
    private static final String baseUri = "http://users.bugred.ru/tasks/rest/";
    private static final String register = "/doregister";
    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .setContentType("application/json")
            .addFilter(new AllureRestAssured())
            .build();

    @Test
    @Step("Позитивный тест, создание нового пользователя")
    public void PositiveRegistration(String email, String name, String password) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"password\":\"" + password + "\"}")
            .when()
            .post(register)
            .then()
            .assertThat()
            .body("email", equalTo(email))
            .assertThat()
            .body("name", equalTo(name));
    }

    @Test
    @Step("Позитивный тест, пустой пароль")
    public void PositiveEmptyPassword(String email, String name) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"password\":\"\"}")
            .when()
            .post(register)
            .then()
            .body("email", equalTo(email))
            .assertThat()
            .body("name", equalTo(name));
    }

    @Test
    @Step("Негативный тест, повторяющееся имя")
    public void NegativeRepeatedName(String email, String name, String password) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"password\":\"" + password + "\"}")
            .when()
            .post(register)
            .then()
            .assertThat().body("type",containsString("error"))
            .assertThat().body("message",containsString("Текущее ФИО " + name + " уже есть в базе"));
    }

    @Test
    @Step("Негативный тест, ошибочный email")
    public void NegativeWrongEmail(String email, String name, String password) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"password\":\"" + password + "\"}")
            .when()
            .post(register)
            .then()
            .assertThat().body("message",containsString("Некоректный  email " + email));
    }

    @Test
    @Step("Негативный тест, отсутствует пароль")
    public void NegativeMissingPassword(String email, String name) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\"}")
            .when()
            .post(register)
            .then()
            .assertThat().body("message",containsString("Параметр password является обязательным!"));
    }
}
