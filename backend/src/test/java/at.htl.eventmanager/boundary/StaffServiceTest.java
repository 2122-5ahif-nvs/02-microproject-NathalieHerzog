package at.htl.eventmanager.boundary;

import at.htl.eventmanager.repository.StaffRepository;
import com.intuit.karate.junit5.Karate;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class StaffServiceTest {
    @Inject
    StaffRepository staffRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Karate.Test
    Karate addElement() {
        return Karate.run("staff").relativeTo(getClass());
    }

    @Test
    public void testInsertStaff() {
        staffRepository.clearTable();

        String requestBody = "{" +
                "\"name\":\"name1\"," +
                "\"salary\":30.0," +
                "\"isOccupied\":true," +
                "\"engagements\":[{ "+
                "\"event\": {}," +
                "\"job\": \"testJob\"" +
                "}] }";

        String check = "[{\"id\":1,\"name\":\"name1\",\"occupied\":false,\"salary\":30.0}]";

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/api/staff/");

        String actual = given()
                .when()
                .get("http://localhost:8080/api/staff/")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo(check);
    }

    @Test
    public void testDeleteStaff() {
        staffRepository.clearTable();

        String requestBody = "{" +
                "\"name\":\"name1\"," +
                "\"salary\":30.0," +
                "\"isOccupied\":true," +
                "\"engagements\":[{ "+
                "\"event\": {}," +
                "\"job\": \"testJob\"" +
                "}] }";


        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/api/staff/");

        String actual = given()
                .when()
                .delete("http://localhost:8080/api/staff/1")
                .then()
                .statusCode(204)
                .extract()
                .body()
                .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo("");
    }

    @Test
    public void testUpdateStaff() {
        staffRepository.clearTable();

        String requestBody = "{" +
                "\"name\":\"name1\"," +
                "\"salary\":30.0," +
                "\"isOccupied\":true," +
                "\"engagements\":[{ "+
                "\"event\": {}," +
                "\"job\": \"testJob\"" +
                "}] }";


        String updatedRequestBody = "{" +
                "\"name\":\"name2\"," +
                "\"salary\":35.0," +
                "\"isOccupied\":true," +
                "\"engagements\":[{ "+
                "\"event\": {}," +
                "\"job\": \"testJobUpdated\"" +
                "}] }";


        String check = "{\"id\":1,\"name\":\"name2\",\"occupied\":false,\"salary\":35.0}";

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/api/staff/");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .put("http://localhost:8080/api/staff/1");

        String actual = given()
                .when()
                .get("http://localhost:8080/api/staff/1")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo(check);
    }
}
