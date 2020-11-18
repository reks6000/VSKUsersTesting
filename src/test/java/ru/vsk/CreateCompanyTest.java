package ru.vsk;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
import org.testng.annotations.Test;


public class CreateCompanyTest {
    private static final String baseUri = "http://users.bugred.ru/tasks/rest/";
    private static final String createCompany = "/createcompany";
    private RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri(baseUri)
            .setContentType("application/json")
            .addFilter(new AllureRestAssured())
            .build();

    @Test
    @Step("Позитивный тест, создание компании")
    public void PositiveCreateCompany(String companyName, String companyType, String companyUsers, String emailOwner) {
        given().spec(requestSpec).body("{\"company_name\": \"" + companyName + "\",\"company_type\": \"" + companyType + "\",\"company_users\": " + companyUsers + ",\"email_owner\": \"" + emailOwner + "\"}")
                .when()
                .post(createCompany)
                .then()
                .assertThat()
                .body("company.name", equalTo(companyName))
                .assertThat()
                .body("company.type", equalTo(companyType));
    }

    @Test
    @Step("Позитивный тест, создание с повторяющимся названием")
    public void PositiveCreateSameNameCompany(String companyName, String companyType, String companyUsers, String emailOwner) {
        given().spec(requestSpec).body("{\"company_name\": \"" + companyName + "\",\"company_type\": \"" + companyType + "\",\"company_users\": " + companyUsers + ",\"email_owner\": \"" + emailOwner + "\"}")
                .when()
                .post(createCompany)
                .then()
                .assertThat()
                .body("company.name", equalTo(companyName))
                .assertThat()
                .body("company.type", equalTo(companyType));
    }

    @Test
    @Step("Негативный тест, неверный тип")
    public void NegativeWrongType(String companyName, String companyUsers, String emailOwner) {
        given().spec(requestSpec).body("{\"company_name\": \"" + companyName + "\",\"company_type\": \"" + "ИП " + "\",\"company_users\": " + companyUsers + ",\"email_owner\": \"" + emailOwner + "\"}")
                .when()
                .post(createCompany)
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .body("type", equalTo("error"))
                .assertThat()
                .body("message", equalTo(" company_type ИП  некорректный"));
    }

    @Test
    @Step("Негативный тест, отутствует имя")
    public void NegativeMissingName(String companyType, String companyUsers, String emailOwner) {
        given().spec(requestSpec).body("{\"company_type\": \"" + companyType + "\",\"company_users\": " + companyUsers + ",\"email_owner\": \"" + emailOwner + "\"}")
                .when()
                .post(createCompany)
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .body("type", equalTo("error"))
                .assertThat()
                .body("message", equalTo("Параметр company_name является обязательным!"));
    }

    @Test
    @Step("Негативный тест, несуществующие сотрудники")
    public void NegativeMissingUsers(String companyName, String companyType, String companyUsers, String emailOwner) {
        given().spec(requestSpec).body("{\"company_name\": \"" + companyName + "\",\"company_type\": \"" + companyType + "\",\"company_users\": " + companyUsers + ",\"email_owner\": \"" + emailOwner + "\"}")
                .when()
                .post(createCompany)
                .then()
                .assertThat()
                .statusCode(200)
                .assertThat()
                .body("type", equalTo("error"))
                .assertThat()
                .body("message", equalTo(" company_users  не указаны сотрудники"));
    }
}
