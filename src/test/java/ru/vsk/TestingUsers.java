package ru.vsk;

import io.qameta.allure.Step;
import org.testng.annotations.Test;

public class TestingUsers {

    private String companyUsers, emailOwner;
    private FakeDataGenerator gen = new FakeDataGenerator();
    private RegisterTest registerTest = new RegisterTest();
    private CreateCompanyTest createCompanyTest = new CreateCompanyTest();
    private CreateUserTest createUserTest = new CreateUserTest();

    @Test
    @Step("Тестирование метода doRegister")
    public void TestingRegister() {
        String email1, email2;
        String nameForReuse = gen.GenerateName();
        registerTest.PositiveRegistration(email1 = gen.GenerateEmail(), nameForReuse, gen.GeneratePassword());
        registerTest.PositiveEmptyPassword(email2 = gen.GenerateEmail(), gen.GenerateName());
        registerTest.NegativeRepeatedName(gen.GenerateEmail(), nameForReuse, gen.GeneratePassword());
        registerTest.NegativeWrongEmail(gen.GenerateOther(), gen.GenerateName(), gen.GeneratePassword());
        registerTest.NegativeMissingPassword(gen.GenerateEmail(), gen.GenerateName());
        this.companyUsers = "[\"" + email1 + "\", \"" + email2 + "\"]";
        this.emailOwner = email1;

    }

    @Test(dependsOnMethods={"TestingRegister"})
    @Step("Тестирование метода CreateCompany")
    public void TestingCreateCompany() throws InterruptedException{
        String nameForReuse = gen.GenerateCompanyName();
        createCompanyTest.PositiveCreateCompany(nameForReuse, gen.GenerateCompanyType(), companyUsers, emailOwner);
        createCompanyTest.PositiveCreateSameNameCompany(nameForReuse, gen.GenerateCompanyType(), companyUsers, emailOwner);
        createCompanyTest.NegativeWrongType(gen.GenerateCompanyName(), companyUsers, emailOwner);
        createCompanyTest.NegativeMissingName(gen.GenerateCompanyType(), companyUsers, emailOwner);
        createCompanyTest.NegativeMissingUsers(gen.GenerateCompanyName(), gen.GenerateCompanyType(), "[\""+gen.GenerateEmail()+"\"]", emailOwner);
    }

    @Test
    @Step("Тестирование метода CreateUser")
    public void TestingCreateUser() {
        String tasks = "[10, 15]";
        String companies = "[36, 375]";
        createUserTest.PositiveCreateUser(gen.GenerateEmail(), gen.GenerateName(), tasks, companies);
        createUserTest.PositiveCreateUserMax(gen.GenerateEmail(), gen.GenerateUsername(), gen.GenerateName(), gen.GenerateOther(), gen.GenerateSurname(), gen.GenerateFatherName(), gen.GeneratePhone(), gen.GenerateAddress(), gen.GenerateGender(), gen.GeneratePastDate(), gen.GeneratePastDate(), tasks, companies);
        createUserTest.NegativeByUserChange(gen.GenerateEmail(), gen.GenerateName(), tasks, companies);
        createUserTest.NegativeWrongInn(gen.GenerateEmail(), gen.GenerateName(), tasks, companies, gen.GenerateOther());
        createUserTest.NegativeFutureBirthday(gen.GenerateEmail(), gen.GenerateName(), tasks, companies, gen.GenerateFutureDate());
    }
}
