package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.model.Order;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    private static final String CREATE_ORDER = "/api/v1/orders";

    @Step("Создание заказа: {order}")
    public ValidatableResponse createOrder(Order order){
        return given()
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then();
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .when()
                .get(CREATE_ORDER)
                .then();
    }
}
