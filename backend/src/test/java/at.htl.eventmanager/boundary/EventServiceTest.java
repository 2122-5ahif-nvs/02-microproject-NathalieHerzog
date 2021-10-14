package at.htl.eventmanager.boundary;

import at.htl.eventmanager.repository.EventRepository;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class EventServiceTest {
    @Inject
    EventRepository eventRepository;

    @Test
    public void testInsertEvent() {
        eventRepository.clearTable();

        String requestBody = "{" +
                "\"title\":\"title1\"," +
                "\"managerId\": 6," +
                "\"startDate\": \"04-05-2020\"," +
                "\"endDate\": \"11-12-2021\"," +
                "\"sponsorship\":{\"id\": 1}," +
                "\"engagement\":{\"id\": 1}" +
                "}";

        String check = "[{\"endDate\":\"11-12-2021\",\"id\":1,\"managerId\":6,\"startDate\":\"04-05-2020\",\"title\":\"title1\"}]";

        given()
            .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
            .body(requestBody)
            .when()
                .post("http://localhost:8080/api/event/");

        String actual = given()
                .when()
                    .get("http://localhost:8080/api/event/")
                .then()
                    .statusCode(200)
                    .extract()
                    .body()
                    .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo(check);
    }

    @Test
    public void testDeleteEvent() {
        String requestBody = "{" +
                "\"title\":\"title1\"," +
                "\"managerId\": 6," +
                "\"startDate\": \"04-05-2020\"," +
                "\"endDate\": \"11-12-2021\"," +
                "\"sponsorship\":{\"id\": 1}," +
                "\"engagement\":{\"id\": 1}" +
                "}";

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/api/event/");

        String actual = given()
                .when()
                .delete("http://localhost:8080/api/event/1")
                .then()
                .statusCode(204)
                .extract()
                .body()
                .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo("");
    }

    @Test
    public void testUpdateEvent() {
        eventRepository.clearTable();

        String requestBody = "{" +
                "\"title\":\"title1\"," +
                "\"managerId\": 6," +
                "\"startDate\": \"04-05-2020\"," +
                "\"endDate\": \"11-12-2021\"," +
                "\"sponsorship\":{\"id\": 1}," +
                "\"engagement\":{\"id\": 1}" +
                "}";

        String updatedRequestBody = "{" +
                "\"title\":\"updatedTitle1\"," +
                "\"managerId\": 5," +
                "\"startDate\": \"04-06-2020\"," +
                "\"endDate\": \"02-12-2022\"," +
                "\"sponsorship\":{\"id\": 1}," +
                "\"engagement\":{\"id\": 1}" +
                "}";

        String check = "{\"endDate\":\"02-12-2022\",\"id\":1,\"managerId\":5,\"startDate\":\"04-06-2020\",\"title\":\"updatedTitle1\"}";

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/api/event/");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .put("http://localhost:8080/api/event/1");

        String actual = given()
                .when()
                .get("http://localhost:8080/api/event/1")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo(check);
    }
}
