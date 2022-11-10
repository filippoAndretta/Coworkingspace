package ch.zli.m223;

import org.junit.jupiter.api.Test;
import java.util.List;

import java.util.UUID;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

import ch.zli.m223.model.CwSUser;

@TransactionalQuarkusTest
public class GreetingResourceTest {

    @Test
    public List<CwSUser> index() {
        given()
          .when().get("/hello")
          .then()
             .statusCode(200)
             .body(is("hello"));
    }

    @Test
    public void testGreetingEndpoint() {
        String uuid = UUID.randomUUID().toString();
        given()
          .pathParam("name", uuid)
          .when().get("/hello/greeting/{name}")
          .then()
            .statusCode(200)
            .body(is("hello " + uuid));
    }

}
