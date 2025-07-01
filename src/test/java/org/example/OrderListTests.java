package org.example;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.example.steps.OrderSteps;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.filters;
import static org.apache.http.HttpStatus.SC_OK;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

@DisplayName("Тесты на получение списка заказов")
public class OrderListTests extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @Before
    public void setUp() {
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    @DisplayName("Получение списка заказов")
    @Description("Проверяет, что API возвращает список заказов со статусом 200 и непустым списком")
    public void shouldReturnOrderList() {
        orderSteps.getOrderList()
                .statusCode(SC_OK)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}
