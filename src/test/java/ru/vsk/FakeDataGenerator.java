package ru.vsk;

import com.github.javafaker.Faker;
import com.github.javafaker.service.FakeValuesService;
import com.github.javafaker.service.RandomService;

import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.concurrent.TimeUnit;

public class FakeDataGenerator {
    private FakeValuesService fakeValuesService;
    private Faker faker;
    private SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");

    public FakeDataGenerator() {
        this.fakeValuesService = new FakeValuesService(new Locale("en-GB"), new RandomService());
        this.faker = new Faker();
    }

    public String GenerateEmail() {
        return fakeValuesService.regexify("[a-z0-9]{1,10}[@][a-z]{1,6}[.][a-z]{2,3}");
    }

    public String GenerateName() {
        return faker.name().firstName();
    }

    public String GenerateSurname() {
        return faker.name().lastName();
    }

    public String GenerateFatherName() {
        return faker.name().firstName();
    }

    public String GeneratePassword() {
        return fakeValuesService.regexify("[\\w]+");
    }

    public String GenerateCompanyName() {
        return faker.company().name();
    }

    public String GenerateCompanyType() {
        return fakeValuesService.regexify("(ИП|ООО|ОАО)");
    }

    public String GenerateUsername() {
        return faker.name().username();
    }

    public String GenerateAddress() {
        return faker.address().streetAddress() + faker.address().buildingNumber();
    }

    public String GenerateGender() {
        return fakeValuesService.regexify("(m|f|M|F)");
    }

    public String GeneratePhone() {
        return fakeValuesService.numerify("[### ## ##]");
    }

    public String GeneratePastDate() {
        return formatter.format(faker.date().past(30, TimeUnit.DAYS));
    }

    public String GenerateFutureDate() {
        return formatter.format(faker.date().future(30, TimeUnit.DAYS));
    }

    public String GenerateOther() {
        return fakeValuesService.regexify("[A-Za-z0-9]{1,30}");
    }

    public String GenerateTasks() {
        return fakeValuesService.numerify("[##]");
    }

    public String GenerateCompanies() {
        return fakeValuesService.numerify("[##]");
    }
}
