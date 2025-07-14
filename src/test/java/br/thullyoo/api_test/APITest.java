package br.thullyoo.api_test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class APITest {

    @BeforeEach
    public void setup(){
        RestAssured.baseURI = "http://localhost:8001/tasks-backend";
    }

    @Test
    public void shouldReturnTasks(){
        RestAssured
                .given()
                .when()
                    .get("todo")
                .then()
                    .log().all()
                    .statusCode(200)

        ;
    }

    @Test
    public void shouldSaveTaskWithSuccess(){
        RestAssured
                .given()
                    .body("{\"task\": \"task1\", \"dueDate\": \"2030-12-10\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("todo")
                .then()
                    .statusCode(201);
    }

    @Test
    public void shouldntSaveInvalidTask(){
        RestAssured
                .given()
                    .body("{\"task\": \"task1\", \"dueDate\": \"2010-12-10\"}")
                    .contentType(ContentType.JSON)
                .when()
                    .post("todo")
                .then()
                    .statusCode(400)
                    .body("message", CoreMatchers.is("Due date must not be in past"));
    }
}
