package ru.vsk;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;


public class CreateUserTest {
    private static final String baseUri = "http://users.bugred.ru/tasks/rest/";
    private static final String createUser = "/createuser";
    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .setContentType("application/json")
            .addFilter(new AllureRestAssured())
            .build();



    @Test
    @Step("Позитивный тест, создание пользователя")
    public void PositiveCreateUser(String email, String name, String tasks, String companies) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"tasks\": " + tasks + ",\"companies\": " + companies + "}")
                .when()
                .post(createUser)
                .then()
                .assertThat()
                .body("email", equalTo(email))
                .assertThat()
                .body("name", equalTo(name));
    }

    @Test
    @Step("Позитивный тест, создание пользователя со всеми данными")
    public void PositiveCreateUserMax(String email, String username, String name, String other, String surname, String fathername, String phone, String address, String gender, String birthday, String dayStart, String tasks, String companies) {

        given().spec(requestSpec).body("{" +
                "    \"email\": \"" + email + "\"," +
                "    \"name\": \"" + username + "\"," +
                "    \"name1\": \"" + name + "\"," +
                "    \"hobby\": \"" + other + "\"," +
                "    \"surname1\": \"" + surname + "\"," +
                "    \"fathername1\": \"" + fathername + "\"," +
                "    \"cat\": \"" + other + "\"," +
                "    \"dog\": \"" + other + "\"," +
                "    \"parrot\": \"" + other + "\"," +
                "    \"cavy\": \"" + other + "\"," +
                "    \"hamster\": \"" + other + "\"," +
                "    \"squirrel\": \"" + other + "\"," +
                "    \"phone\": \"" + phone + "\"," +
                "    \"adres\": \"" + address + "\"," +
                "    \"gender\": \"" + gender + "\"," +
                "    \"birthday\":\"" + birthday + "\"," +
                "    \"date_start\":\"" + dayStart + "\"," +
                "    \"companies\": " + companies + "," +
                "    \"tasks\": " + tasks + "}")
                .when()
                .post(createUser)
                .then()
                .assertThat()
                .body("email", equalTo(email))
                .assertThat()
                .body("name", equalTo(username));
    }

    @Test
    @Step("Негативный тест, попытка поменять защищённый параметр")
    public void NegativeByUserChange(String email, String name, String tasks, String companies) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"tasks\": " + tasks + ",\"companies\": " + companies + ",\"by_user\": \"mannnnnnnnager@mail.ru\"}")
                .when()
                .post(createUser)
                .then()
                .assertThat()
                .body("by_user", equalTo("manager@mail.ru"));
    }

    @Test
    @Step("Негативный тест, неверный ИНН")
    public void NegativeWrongInn(String email, String name, String tasks, String companies, String inn) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"tasks\": " + tasks + ",\"companies\": " + companies + ",\"inn\": \"" + inn + "\"}")
                .when()
                .post(createUser)
                .then()
                .assertThat()
                .body("message", equalTo(" Значение " + inn + " ИНН ФЛ должен содержать 12 цифр"));
    }

    @Test
    @Step("Негативный тест, день рождения в будущем")
    public void NegativeFutureBirthday(String email, String name, String tasks, String companies, String birthday) {
        given().spec(requestSpec).body("{\"email\":\"" + email + "\",\"name\":\"" + name + "\",\"tasks\": " + tasks + ",\"companies\": " + companies + ",\"birthday\": \"" + birthday +"\"}")
                .when()
                .when()
                .post(createUser)
                .then()
                .assertThat()
                .body("type", equalTo("error"));
    }

}
