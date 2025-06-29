package org.example.steps;

import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;
import org.example.model.Courier;

import static io.restassured.RestAssured.given;

public class CourierSteps {

    private static final String CREATE_COURIER = "/api/v1/courier/";
    private static final String LOGIN_COURIER = "/api/v1/courier/login";


    @Step("Создание курьера: {courier}")
    public ValidatableResponse createCourier(Courier courier){
       return given()
                .body(courier)
                .when()
                .post(CREATE_COURIER)
               .then();

    }

    @Step("Логин курьера: {courier}")
    public ValidatableResponse loginCourier(Courier courier){
        return given()
                .body(courier)
                .when()
                .post(LOGIN_COURIER)
                .then();
    }

    @Step("Удаление курьера c id = {courier.id}")
    public ValidatableResponse deleteCourier(Courier courier){
        return given()
                .pathParams("id", courier.getId())
                .when()
                .delete(CREATE_COURIER+"{id}")
                .then();

    }
}
