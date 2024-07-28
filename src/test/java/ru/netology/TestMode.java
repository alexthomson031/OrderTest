package ru.netology;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static com.codeborne.selenide.Selenide.*;
import static ru.netology.DataGenerator.*;

public class TestMode {

    @BeforeEach
    void setUpAll() {
        open("http://localhost:9999");
    }

    @Test
    void returnRegisteredUserAndActive() {
        RegistrationInfo user = registeredActiveUser();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("h2").shouldHave(Condition.exactText(" Личный кабинет"), Duration.ofSeconds(7));
    }

    @Test
    void returnFailPassword() {
        RegistrationInfo user = noRegisteredPassword();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(7));
    }

    @Test
    void returnFailName() {
        RegistrationInfo user = noRegisteredName();
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $("[data-test-id='error-notification'] .notification__content")
                .shouldHave(Condition.exactText("Ошибка! Неверно указан логин или пароль"), Duration.ofSeconds(7));
    }

    @Test
    void returnFailStatus() {
        RegistrationInfo user = registeredBlockedUser("blocked");
        $("[data-test-id='login'] input").setValue(user.getLogin());
        $("[data-test-id='password'] input").setValue(user.getPassword());
        $("[data-test-id='action-login']").click();
        $(".notification__content")
                .shouldHave(Condition.exactText("Ошибка! Пользователь заблокирован"), Duration.ofSeconds(15));
    }
}