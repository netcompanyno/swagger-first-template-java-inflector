package no.nc.ping;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PingControllerTest {

    @Autowired
    TestRestTemplate restTemplate;

    @Before
    public void setUp() {
        String rootUri = restTemplate.getRootUri();
        RestAssured.baseURI = rootUri;
    }

    @Test
    public void ping_with_empty_message_should_return_empty_message_and_timestamp() {
        RestAssured
                .given()
                    .log().all()
                .when()
                    .get("" +
                            "/api/ping")
                .then()
                    .assertThat()
                        .body("message", nullValue())
                        .body("dateAndTime", notNullValue());
    }

    @Test
    public void ping_with_message_should_return_message() {
        String message = "Hello";

        final Response response = RestAssured
                .given()
                    .param("message", message)
                    .log().all()
                .when()
                    .get("/api/ping");
        response
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .assertThat()
                        .body("message", equalTo(message))
                        .body("dateAndTime", notNullValue());
    }
}
