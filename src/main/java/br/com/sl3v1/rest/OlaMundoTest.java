package br.com.sl3v1.rest;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Assert;
import org.junit.Test;

import static io.restassured.RestAssured.*;
import static io.restassured.http.Method.GET;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class OlaMundoTest {

    @Test
    public void testOlaMundo() {
        Response response = request(GET, "http://restapi.wcaquino.me/ola");
        assertTrue(response.getBody().asString().equals("Ola Mundo!"));
        assertTrue(response.statusCode() == 200);
        assertTrue("O status code devveria ser 200", response.statusCode() == 200);
        assertEquals(200, response.statusCode());

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);
    }

    @Test
    public void devoConhecerOutrasFormasRestAssured(){
        Response response = request(GET, "http://restapi.wcaquino.me/ola");
        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);

        get("http://restapi.wcaquino.me/ola").then().statusCode(201);

        given()
                //Aqui vão as pré condições: quando vamos fazer uma requisição podemos mandar dados pelo cabeçalho e pelo corpo da mensagem, é aqui que esses dados são alocados.
        .when()
                //Aqui é onde a ação acontece, onde vão os métodos HTTP, GET, POST, PUT, DELETE, PATCH, OPTIONS
                .get("http://restapi.wcaquino.me/ola")
        .then()
                .assertThat()
                //Aqui é onde acontecem as verificações, inclusive com o encadeamento de várias delas.
                .statusCode(201);

    }

}
