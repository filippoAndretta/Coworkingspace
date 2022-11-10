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
import ch.zli.m223.model.BookingApproval;

@TransactionalQuarkusTest
public class BookingApprovalControllerTest {
    
    Booking booking;
    CwSUser cwSUser;
    BookingApproval bookingApproval;

    @BeforeEach
    public void prepareTesting(){
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

        bookingApproval = new BookingApproval();
        bookingApproval.setApproval(true);
    }

    @Test
    @TestSecurity(user = "Admin", roles = "Admin")
    public void testUpdateEndpoint() {
        var bookingApproval = new BookingApproval();
        
        var createResponse = given()
            .contentType(ContentType.JSON)
            .body(bookingApproval)
            .when().post("/bookingapproval");

        var updateApproval = new BookingApproval();
        given()
            .contentType(ContentType.JSON)
            .body(updateApproval)
            .when().put("/bookingapproval/" + createResponse.jsonPath().get("id"))
            .then()
                .statusCode(200)
                .body(is("[]"));
    }
}
