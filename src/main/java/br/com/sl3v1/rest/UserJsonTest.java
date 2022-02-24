package br.com.sl3v1.rest;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.request;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertEquals;

public class UserJsonTest {
    @Test
    public void deveVerificarPrimeiroNivel() {
        given()
        .when()
                .get("https://restapi.wcaquino.me/users/1")
        .then()
                .statusCode(200)
                .body("id", is(1))
                .body("name", containsString("Silva"))
                .body("age", greaterThan(31))
        ;
    }

    @Test
    public void deveVerificarPrimeiroNivelOutrasFormas() {
        Response response = request(Method.GET, "https://restapi.wcaquino.me/users/1");
        //path
        assertEquals(Integer.valueOf(1), response.path("id"));
        assertEquals(Integer.valueOf(1), response.path("%s", "id"));

        //jsonpath
        JsonPath jsonPath = new JsonPath((response.asString()));
        assertEquals(1, jsonPath.getInt("id"));

        //from

        int id = JsonPath.from(response.asString()).getInt("id");
        assertEquals(1, id);
    }

    @Test
    public void deveVerificarSegundonivel(){
        given()
                .when()
                .get("https://restapi.wcaquino.me/users/2")
                .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"))
                .body("endereco.numero",greaterThan(-1))
        ;
    }
}
