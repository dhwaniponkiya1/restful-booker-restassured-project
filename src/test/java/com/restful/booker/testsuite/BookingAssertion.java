package com.restful.booker.testsuite;

import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasKey;

public class BookingAssertion {

    static ValidatableResponse response;

    @BeforeClass
    public static void inIt() {
        RestAssured.baseURI = "https://restful-booker.herokuapp.com";
        response = given()
                .when()
                .get("/booking/10")
                .then().statusCode(200);
    }

    @Test
    public void tes001() {
        response.body("", hasKey("firstname"));
    }

    @Test
    public void tes002() {
        response.body("", hasKey("lastname"));
    }

    @Test
    public void tes003() {
        response.body("", hasKey("totalprice"));
    }

    @Test
    public void tes004() {
        response.body("", hasKey("depositpaid"));
    }

    @Test
    public void tes005() {
        response.body("bookingdates", hasKey("checkin"));
    }

@Test
    public void tes006() {
        response.body("bookingdates", hasKey("checkout"));
    }


}
