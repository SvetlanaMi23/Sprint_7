package org.example;


import org.example.model.Order;
import org.example.steps.OrderSteps;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.hamcrest.Matchers.notNullValue;

@RunWith(Parameterized.class)
public class OrderTests extends BaseTest {

    private final List<String> color;
    private final OrderSteps orderSteps = new OrderSteps();

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

    private Order order;

    @Before
    public void setUp() {
        order = new Order(
                "Коля", "Николаев", "Москва, ул. Мира, д.1", "Динамо",
                "+79990000000", 5, "2025-07-01", "Позвонить за 1 час", color
        );
    }

    @Test
    public void shouldCreateOrderWithDifferentColors() {
        orderSteps.createOrder(order)
                .statusCode(SC_CREATED)
                .body("track", notNullValue());
    }
}
