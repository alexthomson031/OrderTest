package ru.netology;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.filter.log.LogDetail;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;
import lombok.experimental.UtilityClass;
import lombok.val;
import org.junit.jupiter.api.BeforeAll;

import java.util.Locale;

import static io.restassured.RestAssured.given;

@UtilityClass
public class DataGenerator {
    Faker faker = new Faker(new Locale("en"));

    private static final RequestSpecification requestSpec = new RequestSpecBuilder()
            .setBaseUri("http://localhost")
            .setPort(9999)
            .setAccept(ContentType.JSON)
            .setContentType(ContentType.JSON)
            .log(LogDetail.ALL)
            .build();

    @BeforeAll
    static void setUpAll(RegistrationInfo registrationInfo) {
        given()
                .spec(requestSpec)
                .body(registrationInfo)
                .when()
                .post("/api/system/users")
                .then()
                .statusCode(200);
    }

    public static RegistrationInfo registeredActiveUser() {
        String login = faker.name().firstName();
        String password = faker.internet().password();
        val registrationInfo = new RegistrationInfo(login, password, "active");
        setUpAll(registrationInfo);
        return registrationInfo;
    }

    public static RegistrationInfo noRegisteredPassword() {
        String login = faker.name().firstName();
        String password = faker.internet().password();
        val registrationInfo = new RegistrationInfo(login, password, "active");
        setUpAll(registrationInfo);
        return new RegistrationInfo(login, "password", "active");
    }

    public static RegistrationInfo noRegisteredName() {
        String login = faker.name().firstName();
        String password = faker.internet().password();
        val registrationInfo = new RegistrationInfo(login, password, "active");
        setUpAll(registrationInfo);
        return new RegistrationInfo("Andrey", password, "active");
    }

    public static RegistrationInfo registeredBlockedUser(String status) {
        String login = faker.name().firstName();
        String password = faker.internet().password();
        val registrationInfo = new RegistrationInfo(login, password, status);
        setUpAll(registrationInfo);
        return registrationInfo;
    }
}
