package org.example;

import io.restassured.RestAssured;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.apache.commons.lang3.RandomStringUtils;
import org.example.model.Courier;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.startsWith;

public class CourierTests extends BaseCourierTest {

    @Before
    public void setUp() {
        RestAssured.filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
        courier = new Courier();
        courier.setLogin(RandomStringUtils.randomAlphabetic(12))
                .setPassword(RandomStringUtils.randomAlphabetic(12))
                .setFirstName(RandomStringUtils.randomAlphabetic(12));
    }

    //Тест "Создание курьера":
    @Test
    public void shouldCreateCourierTest() {
        courierSteps
                .createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));

    }

    //Тест "Нельзя создать двух одинаковых курьеров":
    @Test
    public void shoudNotCreateTwoIdenticalCouriers() {
        courierSteps.createCourier(courier).statusCode(201);
        courierSteps.createCourier(courier)
                .statusCode(409)
                .body("message", startsWith("Этот логин уже используется"));
    }


    //Тест "Курьер создается без заполнения поля firstName":
    @Test
    public void shouldCreateCourierWithoutFirstName() {
        courier.setFirstName(null);
        // firstName не устанавливаем — оно null

        courierSteps.createCourier(courier)
                .statusCode(201)
                .body("ok", is(true));
    }


    //Тест "Нельзя создать курьера без логина":
    @Test
    public void shouldNotCreateCourierWithoutLogin() {
        courier.setLogin(null);
        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }

    //Тест "Нельзя создать курьера без пароля":
    @Test
    public void shouldNotCreateCourierWithoutPassword() {
        courier.setPassword(null);
        courierSteps.createCourier(courier)
                .statusCode(400)
                .body("message", is("Недостаточно данных для создания учетной записи"));
    }
}
