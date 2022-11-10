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
public class CwSUserControllerTest {
    
    Booking booking;
    CwSUser cwSUser;

    @BeforeEach
    public void prepareTesting() {
        cwSUser = new CwSUser();
        cwSUser.setEmail("filippo.andretta@bluewin.ch");
        cwSUser.setFirstname("filippo");
        cwSUser.setLastname("andretta");
        cwSUser.setPassword("qwertz123");
        cwSUser.setAdmin(true);

        var createResponse = given().when().post("/user");

        Long id = Integer.toUnsignedLong(createResponse.jsonPath().get("id"));
        cwSUser.setId(id);

        booking = new Booking();
        booking.setBookingApproval(true);
        booking.setDate(LocalDate.now());
        booking.setCwSUser(cwSUser);
    }

    @Test
    @TestSecurity(user = "Admin", roles = { "User", "Admin" })
    public void testindex() {
        given().when().get("/users").then()
            .statusCode(200)
            .body(is("[]"));
    }

    @Test
    @TestSecurity(user = "Admin", roles = { "User", "Admin" })
    public void testDeleteEndpoint() {
        var cwSUser = new CwSUser();

        var createResponse = given()
            .contentType(ContentType.JSON)
            .body(cwSUser)
            .when().post("/users");
        
        given()
            .when().delete("/users/" + createResponse.jsonPath().get("id"))
            .then()
                .statusCode(204);
    }

    @Test
    public void testUpdateEndpoint() {
        var cwSUser = new CwSUser();
        
        var createResponse = given()
            .contentType(ContentType.JSON)
            .body(cwSUser)
            .when().post("/users");

        var updateCwSUser = new CwSUser();
        given()
            .contentType(ContentType.JSON)
            .body(updateCwSUser)
            .when().put("/users/" + createResponse.jsonPath().get("id"))
            .then()
                .statusCode(200)
                .body(is("[]"));
    }
}
