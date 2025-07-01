package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.model.Order;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class OrderSteps {
    private static final String CREATE_ORDER = "/api/v1/orders";
    private static final String CANCEL_ORDER = "/api/v1/orders/cancel?track={track}";

    @Step("Создание заказа: {order}")
    public ValidatableResponse createOrder(Order order){
        return given()
                .header("Content-type", "application/json")
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

    @Step("Отмена заказа по треку: {track}")
    public ValidatableResponse cancelOrder(int track) {
        return given()
                .header("Content-type", "application/json")
                .pathParams(Map.of("track", track))
                .log().all()          // лог запроса
                .when()
                .put(CANCEL_ORDER)
                .then()
                .log().all();         // лог ответа;
    }
}
