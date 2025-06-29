package org.example;

import io.restassured.filter.log.RequestLoggingFilter;
import io.restassured.filter.log.ResponseLoggingFilter;
import org.example.steps.OrderSteps;
import org.junit.Before;
import org.junit.Test;

import static io.restassured.RestAssured.filters;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.notNullValue;

public class OrderListTests extends BaseTest {

    private final OrderSteps orderSteps = new OrderSteps();

    @Before
    public void setUp() {
        filters(new RequestLoggingFilter(), new ResponseLoggingFilter());
    }

    @Test
    public void shouldReturnOrderList() {
        orderSteps.getOrderList()
                .statusCode(200)
                .body("orders", notNullValue())
                .body("orders.size()", greaterThan(0));
    }
}
