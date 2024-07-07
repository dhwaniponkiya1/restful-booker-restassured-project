package com.restful.booker.crudtest;

import com.restful.booker.model.BookingPojo;
import com.restful.booker.testbase.TestBase;
import com.restful.booker.utils.TestUtils;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.HashMap;

import static io.restassured.RestAssured.given;

public class BookingCRUDTest extends TestBase {

    static String id;

    @Test(priority = 0)
    public void verifyBookingCreatedSuccessfully() {
        String fName = "John" + TestUtils.getRandomValue();
        String lName = "Vialy" + TestUtils.getRandomValue();
        int totalPrice = 900;
        boolean depositPaid = true;
        HashMap<String, String> bookingDates = new HashMap<>();
        String checkIn = "2020-05-03";
        String checkOut = "2020-05-10";
        bookingDates.put("checkin", checkIn);
        bookingDates.put("checkout", checkOut);
        String additionalNeeds = "Lunch";

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(fName);
        bookingPojo.setLastname(lName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(depositPaid);
        bookingPojo.setBookingdates(bookingDates);
        bookingPojo.setAdditionalneeds(additionalNeeds);

        Response response = given().log().ifValidationFails()
                .header("Content-Type", "application/json")
                .when()
                .body(bookingPojo)
                .post("/booking");

        id = response.jsonPath().getString("bookingid");

        response.prettyPrint();
        response.then().log().ifValidationFails().statusCode(200);
    }

    @Test(priority = 1)
    public void verifyBookingReadSuccessfully() {
        Response response = given()
                .pathParam("id",id)
                .when()
                .get("/booking/{id}");  // john smith
        response.prettyPrint();
        response.then().statusCode(200);
    }


    @Test(priority = 2)
    public void verifyBookingUpdateSuccessfully(){
        String fName = "John" + "Updated";
        String lName = "Vialy" + "Updated";
        int totalPrice = 900;
        boolean depositPaid = true;

        HashMap<String, String> bookingDates = new HashMap<>();
        String checkIn = "2020-05-03";
        String checkOut = "2020-05-10";
        bookingDates.put("checkin", checkIn);
        bookingDates.put("checkout", checkOut);
        String additionalNeeds = "breakfast,dinner";

        BookingPojo bookingPojo = new BookingPojo();
        bookingPojo.setFirstname(fName);
        bookingPojo.setLastname(lName);
        bookingPojo.setTotalprice(totalPrice);
        bookingPojo.setDepositpaid(depositPaid);
        bookingPojo.setBookingdates(bookingDates);
        bookingPojo.setAdditionalneeds(additionalNeeds);

        Response response =
                given().log().all()
                        .header("Content-Type", "application/json")
                        .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                        .header("Connection", "keep-alive")
                        .pathParam("id",id)
                        .body(bookingPojo)
                        .when()
                        .put("/booking/{id}");
        response.then().statusCode(200);
        response.prettyPrint();
    }

    @Test(priority = 3)
    public void verifyBookingDeleteSuccessfully() {
        Response response = given().log().all()
                .pathParam("id",id)
                .header("Authorization", "Basic YWRtaW46cGFzc3dvcmQxMjM=")
                .when()
                .delete("booking/{id}");
        response.then().statusCode(201);
        response.prettyPrint();
    }


}
