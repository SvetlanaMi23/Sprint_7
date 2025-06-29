package org.example;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import org.example.util.RestConfig;
import org.junit.Before;

public class BaseTest {

    @Before
    public void startUp() {
        RestAssured.requestSpecification = new RequestSpecBuilder()
                .setBaseUri(RestConfig.HOST)
                .setContentType(ContentType.JSON)
                .build();
    }
}
