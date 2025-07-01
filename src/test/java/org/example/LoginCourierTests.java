package org.example;

import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.qameta.allure.Description;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.Courier;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тысты авторизации курьера")
public class LoginCourierTests extends BaseCourierTest {

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12));
    }

    // Вспомогательный метод для создания тестового курьера
    private void createTestCourier() {
        courierSteps.createCourier(courier);
    }

    @Test
    @DisplayName("Успешная авторизация при валидных значениях логин и пароль")
    @Description("Проверяет, что при корректных логин и пароль авторизация успешна")
    public void shouldLoginCourierTest() {
        createTestCourier();
        courierSteps.loginCourier(courier)
                .statusCode(SC_OK)
                .body("id", notNullValue());
    }

    @Test
    @DisplayName("Нельзя авторизоваться под несуществующим пользователем")
    @Description("Проверяет, что невозможно авторизоваться с несуществующими логином или паролем")
    public void shouldNotLoginWithNonExistingUser() {
        Courier nonExistingCourier = new Courier()
                .setLogin("nonexistent_" + RandomStringUtils.randomAlphabetic(6))
                .setPassword("wrongpass");

        courierSteps.loginCourier(nonExistingCourier)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация с неверным паролем невозможна")
    @Description("Проверяет, что при неверном пароле авторизация не проходит")
    public void shouldNotLoginWithWrongPassword() {
        createTestCourier();
        String correctPassword = courier.getPassword();
        courier.setPassword("wrongPassword");
        courierSteps.loginCourier(courier)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
        courier.setPassword(correctPassword);
    }

    @Test
    @DisplayName("Авторизация с неверным логином невозможна")
    @Description("Проверяет, что при неверном логине авторизация не проходит")
    public void shouldNotLoginWithWrongLogin() {
        createTestCourier();
        courier.setLogin("wrongLogin");
        courierSteps.loginCourier(courier)
                .statusCode(SC_NOT_FOUND)
                .body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация без логина невозможна")
    @Description("Проверяет, что без логина система возвращает ошибку 400 Bad Request и сообщением Недостаточно данных для входа")
    public void shouldNotLoginWithoutLogin() {
        createTestCourier();
        courier.setLogin(null);
        courierSteps.loginCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация без пароля невозможна")
    @Description("Проверяет, что без пароля система возвращает ошибку 400 Bad Request и сообщением Недостаточно данных для входа")
    public void shouldNotLoginWithoutPassword() {
        createTestCourier();
        courier.setPassword(null);
        courierSteps.loginCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для входа"));
    }
}
