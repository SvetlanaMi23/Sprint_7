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
import static org.hamcrest.Matchers.startsWith;

@DisplayName("Тысты курьера")
public class CourierTests extends BaseCourierTest {

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier()
                .setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12))
                .setFirstName(RandomStringUtils.randomAlphabetic(12));
    }

    @Test
    @DisplayName("Создание нового курьера")
    @Description("Проверяет, что курьер может быть успешно создан при корректных данных")
    public void shouldCreateCourierTest() {
        courierSteps
                .createCourier(courier)
                .statusCode(SC_CREATED)
                .body("ok", is(true));

    }

    @Test
    @DisplayName("Нельзя создать двух одинаковых курьеров")
    @Description("Проверяет, что при создании курьера с существующим логином приходит ошибка 409 Conflict")
    public void shoudNotCreateTwoIdenticalCouriers() {
        courierSteps.createCourier(courier).statusCode(201);
        courierSteps.createCourier(courier)
                .statusCode(SC_CONFLICT)
                .body("message", startsWith("Этот логин уже используется"));
    }

    @Test
    @DisplayName("Курьер создается без заполнения поля firstName")
    @Description("Проверяет, что курьер создается без указания имени, firstName=null")
    public void shouldCreateCourierWithoutFirstName() {
        courier.setFirstName(null);
        // firstName не устанавливаем — оно null

        courierSteps.createCourier(courier)
                .statusCode(SC_CREATED)
                .body("ok", is(true));
    }

    @Test
    @DisplayName("Нельзя создать курьера без логина")
    @Description("Проверяет, что при создании курьера без логина возвращается ошибка 400 Bad Request")
    public void shouldNotCreateCourierWithoutLogin() {
        courier.setLogin(null);
        courierSteps.createCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    @Test
    @DisplayName("Нельзя создать курьера без пароля")
    @Description("Проверяет, что при создании курьера без пароля возвращается ошибка 400 Bad Request")
    public void shouldNotCreateCourierWithoutPassword() {
        courier.setPassword(null);
        courierSteps.createCourier(courier)
                .statusCode(SC_BAD_REQUEST)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
