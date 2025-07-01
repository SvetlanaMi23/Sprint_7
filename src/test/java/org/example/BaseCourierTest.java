package org.example;

import org.example.model.Courier;
import org.example.steps.CourierSteps;
import org.junit.After;

public class BaseCourierTest extends BaseTest {
    protected final CourierSteps courierSteps = new CourierSteps();
    protected Courier courier;

    @After
    public void tearDown() {
        var loginResponse = courierSteps.loginCourier(courier).extract();
        if (loginResponse.statusCode() == 200 && loginResponse.path("id") != null) {
            courier.setId(loginResponse.path("id"));
            courierSteps.deleteCourier(courier);
        }
    }
}
