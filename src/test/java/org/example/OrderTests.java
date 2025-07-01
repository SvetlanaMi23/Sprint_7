package org.example;


import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.example.model.Order;
import org.example.steps.OrderSteps;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
@DisplayName("Тесты на создание заказов с разными цветами")
public class OrderTests extends BaseTest {

    private final List<String> color;
    private final OrderSteps orderSteps = new OrderSteps();
    private Order order;
    private Integer createdOrderTrack;

    public OrderTests(List<String> color) {
        this.color = color;
    }

    @Parameterized.Parameters(name = "Цвет: {0}")// В отчёте Allure видеть, с каким именно цветом выполнялся тест
    public static Object[][] getColorData() {
        return new Object[][]{
                {Collections.singletonList("BLACK")},
                {Collections.singletonList("GREY")},
                {Arrays.asList("BLACK", "GREY")},
                {Collections.emptyList()}
        };
    }

    @Before
    public void setUp() {
        order = new Order(
                "Коля", "Николаев", "Москва, ул. Мира, д.1", "Динамо",
                "+79990000000", 5, "2025-07-01", "Позвонить за 1 час", color
        );
    }

    @Test
    @DisplayName("Создание заказа с разными вариантами цвета")
    @Description("Создаёт заказ с указанным цветом и проверяет, что возвращается трек заказа'")
    public void shouldCreateOrderWithDifferentColors() {
        ValidatableResponse response = orderSteps.createOrder(order)
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
        createdOrderTrack = response.extract().path("track");
    }

    @After
    public void tearDown() {
        if (createdOrderTrack != null) {
            orderSteps.cancelOrder(createdOrderTrack)
                    .statusCode(SC_OK);
        }
    }
}
