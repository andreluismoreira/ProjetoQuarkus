package com.andre.mp;

import static io.restassured.RestAssured.given;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

@QuarkusTest
public class PratoResourceTest {

    @Test
    public void testeHelloEndpoint() {
        String body = given()
                .when().get("/pratos")
                .then()
                .statusCode(200).extract().asString();
        System.out.println(body);
    }

}
