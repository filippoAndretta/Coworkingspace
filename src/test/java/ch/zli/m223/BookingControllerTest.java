package ch.zli.m223;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import java.time.LocalDate;

import ch.zli.m223.model.Booking;
import ch.zli.m223.model.CwSUser;
import io.quarkus.test.security.TestSecurity;
import io.restassured.http.ContentType;

@TransactionalQuarkusTest
public class BookingControllerTest {
    
    Booking booking;
    CwSUser cwSUser;

    @BeforeEach
    public void prepareTesting(){
        //test entity vobrerieten
        cwSUser = new CwSUser();
        cwSUser.setEmail("filippo.andretta@bluewin.ch");
        cwSUser.setFirstname("filippo");
        cwSUser.setLastname("andretta");
        cwSUser.setPassword("qwertz123");
        cwSUser.setAdmin(true);
        cwSUser.setBooking(null);

        var createResponse = given().when().post("/users");

        Long id = Integer.toUnsignedLong(createResponse.jsonPath().get("id"));
        cwSUser.setId(id);

        booking = new Booking();
        booking.setBookingApproval(true);
        booking.setDate(LocalDate.now());
        booking.setCwSUser(cwSUser);
        booking.setCwSUser(cwSUser);
    }

    @Test
    @TestSecurity(user = "Admin", roles = { "User", "Admin" })
    public void testindex(){
        given().when().get("/booking").then()
            .statusCode(200)
            .body(is("[]"));
    }

    @Test
    @TestSecurity(user = "Admin", roles = { "User", "Admin" })
    public void testDeleteEndpoint() {
        var booking = new Booking();

        var createResponse = given()
            .contentType(ContentType.JSON)
            .body(booking)
            .when().post("/booking");
        
        given()
            .when().delete("/booking/" + createResponse.jsonPath().get("id"))
            .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateEndpoint() {
        var booking = new Booking();
        
        var createResponse = given()
            .contentType(ContentType.JSON)
            .body(booking)
            .when().post("/booking");

        var updateBooking = new Booking();
        given()
            .contentType(ContentType.JSON)
            .body(updateBooking)
            .when().put("/booking/" + createResponse.jsonPath().get("id"))
            .then()
                .statusCode(200)
                .body(is("[]"));
    }
}
