package org.acme.nbody;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.containsString;

@QuarkusTest
public class SimulationResourceTest {

    @Test
    public void testConfigureSimulation() {
        SimulationConfig config = new SimulationConfig(0.5, 0.05, 2);

        given()
            .contentType(ContentType.JSON)
            .body(config)
            .when().post("/api/simulation/config")
            .then()
            .statusCode(200)
            .body(is("Simulation configured successfully"));
    }

    @Test
    public void testAddBody() {
        BodyDTO bodyDTO = new BodyDTO();
        bodyDTO.setMass(10.0);
        bodyDTO.setPosition(new double[]{1.0, 2.0});
        bodyDTO.setVelocity(new double[]{0.0, 0.0});
        bodyDTO.setColor("red");
        bodyDTO.setRadius(5.0);

        given()
            .contentType(ContentType.JSON)
            .body(bodyDTO)
            .when().post("/api/simulation/body")
            .then()
            .statusCode(200)
            .body(is("Body added successfully"));
    }

    @Test
    public void testGetState() {
        // First add a body
        BodyDTO bodyDTO = new BodyDTO();
        bodyDTO.setMass(10.0);
        bodyDTO.setPosition(new double[]{1.0, 2.0});
        bodyDTO.setVelocity(new double[]{0.0, 0.0});
        bodyDTO.setColor("red");
        bodyDTO.setRadius(5.0);

        given()
            .contentType(ContentType.JSON)
            .body(bodyDTO)
            .when().post("/api/simulation/body")
            .then()
            .statusCode(200);

        // Then get state
        given()
            .when().get("/api/simulation/state")
            .then()
            .statusCode(200)
            .contentType(ContentType.JSON);
    }

    @Test
    void testSimulationLifecycle() {
        // Start simulation
        given()
            .contentType(ContentType.JSON)  // Ensure the content type is set to JSON
            .when().post("/api/simulation/start")
            .then()
            .statusCode(200)
            .body(containsString("Simulation started"));

        // Stop simulation
        given()
            .contentType(ContentType.JSON)  // Ensure the content type is set to JSON
            .when().post("/api/simulation/stop")
            .then()
            .statusCode(200)
            .body(containsString("Simulation stopped"));

        // Reset simulation
        given()
            .contentType(ContentType.JSON)  // Ensure the content type is set to JSON
            .when().post("/api/simulation/reset")
            .then()
            .statusCode(200)
            .body(is("Simulation reset"));
    }
}