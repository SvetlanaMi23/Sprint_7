package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.model.Order;

import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.apache.http.HttpStatus.SC_CREATED;

public class OrderSteps {
    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String CANCEL_ORDER = "/api/v1/orders/cancel";

    @Step("Создание заказа: {order}")
    public ValidatableResponse createOrder(Order order){
        return given()
                .header("Content-type", "application/json")
                .body(order)
                .when()
                .post(CREATE_ORDER)
                .then();
    }

    @Step("Создание заказа и возврат трека")
    public int createOrderAndGetTrack(Order order) {
        return createOrder(order)
                .statusCode(SC_CREATED) // 201
                .extract()
                .path("track");
    }

    @Step("Получение списка заказов")
    public ValidatableResponse getOrderList() {
        return given()
                .when()
                .get(CREATE_ORDER)
                .then();
    }

    @Step("Отмена заказа по треку: {track}")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .header("Content-type", "application/json")
                .body(Map.of("track", track))
                .when()
                .put(CANCEL_ORDER)
                .then();
    }
}
