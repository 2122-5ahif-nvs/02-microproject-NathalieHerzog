package at.htl.eventmanager.boundary;

import at.htl.eventmanager.entity.Customer;
import at.htl.eventmanager.entity.Sponsorship;
import at.htl.eventmanager.repository.CustomerRepository;
import com.intuit.karate.junit5.Karate;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import java.util.LinkedList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@QuarkusTest
public class CustomerServiceTest {
    Sponsorship sponsorship;

    @Inject
    CustomerRepository repo;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Karate.Test
    Karate addElement() {
        return Karate.run("customer").relativeTo(getClass());
    }

    @Test
    public void testInsertCustomer() {
        repo.clearTable();

        String requestBody = "{" +
                "\"userName\":\"user1\"," +
                "\"email\":\"mail1@gmail.com\"," +
                "\"newSponsorship\":{\"id\": 1}" +
                "}";

        String check = "[{\"email\":\"mail1@gmail.com\",\"id\":1,\"userName\":\"user1\"}]";

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/api/customer/");

        String actual = given()
                .when()
                .get("http://localhost:8080/api/customer/")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo(check);
    }

    @Test
    public void testDeleteCustomer() {
        String requestBody = "{" +
                "\"userName\":\"user1\"," +
                "\"email\":\"mail1@gmail.com\"," +
                "\"newSponsorship\":{\"id\": 1}" +
                "}";

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/api/customer/");

        String actual = given()
                .when()
                .delete("http://localhost:8080/api/customer/1")
                .then()
                .statusCode(204)
                .extract()
                .body()
                .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo("");
    }

    @Test
    public void testUpdateUsernameAndEmail() {
        String requestBody = "{" +
                "\"userName\":\"user1\"," +
                "\"email\":\"mail1@gmail.com\"," +
                "\"newSponsorship\":{\"id\": 1}" +
                "}";

        String updatedRequestBody = "{" +
                "\"newUser\":\"updatedUser1\"," +
                "\"newMail\":\"updatedMail1@gmail.com\"," +
                "\"newSponsorship\":{\"id\": 1}" +
                "}";

        String check = "{\"email\":\"updatedMail1@gmail.com\",\"id\":1,\"userName\":\"updatedUser1\"}";

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(requestBody)
                .when()
                .post("http://localhost:8080/api/customer/");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .patch("http://localhost:8080/api/customer/username/1");

        given()
                .accept(ContentType.JSON)
                .contentType(ContentType.JSON)
                .body(updatedRequestBody)
                .when()
                .put("http://localhost:8080/api/customer/email/1");

        String actual = given()
                .when()
                .get("http://localhost:8080/api/customer/1")
                .then()
                .statusCode(200)
                .extract()
                .body()
                .asString();
        System.out.println(actual);

        assertThat(actual).isEqualTo(check);
    }
}
