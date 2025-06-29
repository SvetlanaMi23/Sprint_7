package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.Courier;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

public class LoginCourierTests extends BaseCourierTest {

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12));
    }


    // Тест "Курьер может авторизоваться":
    @Test
    public void shouldLoginCourierTest() {

        courierSteps.createCourier(courier);
        courierSteps.loginCourier(courier)
                .statusCode(200)
                .body("id", notNullValue());
    }

    //   Тест "Нельзя авторизоваться под несуществующим пользователем"
    @Test
    public void shouldNotLoginWithNonExistingUser() {
        Courier nonExistingCourier = new Courier()
                .setLogin("nonexistent_" + RandomStringUtils.randomAlphabetic(6))
                .setPassword("wrongpass");

        courierSteps.loginCourier(nonExistingCourier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }


    // Тест "Не сможешь войти в систему с неправильным паролем":
    @Test
    public void shouldNotLoginWithWrongPassword() {
        courierSteps.createCourier(courier);
        String correctPassword = courier.getPassword();
        courier.setPassword("wrongPassword");
        courierSteps.loginCourier(courier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
        courier.setPassword(correctPassword);
    }

    // Тест "Не сможешь войти в систему с неправильным логином":
    @Test
    public void shouldNotLoginWithWrongLogin() {
        courierSteps.createCourier(courier);
        courier.setLogin("wrongLogin");
        courierSteps.loginCourier(courier)
                .statusCode(404)
                .body("message", is("Учетная запись не найдена"));
    }

    // Тест "Не сможешь войти в систему без логина":
    @Test
    public void shouldNotLoginWithoutLogin() {
        courierSteps.createCourier(courier);
        courier.setLogin(null);
        courierSteps.loginCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }

    // Тест "Не сможешь войти в систему без пароля":
    @Test
    public void shouldNotLoginWithoutPassword() {
        courierSteps.createCourier(courier);
        courier.setPassword(null);
        courierSteps.loginCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для входа"));
    }
}
