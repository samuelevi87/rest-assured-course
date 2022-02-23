package br.com.sl3v1.rest;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static io.restassured.RestAssured.request;
import static io.restassured.http.Method.GET;

public class OlaMundoTest {

    @Test
    public void testOaMundo(){
        Response response = request(GET, "http://restapi.wcaquino.me/ola");
        System.out.println(response.getBody().asString().equals("Ola Mundo!"));
        System.out.println(response.statusCode() == 200);

//        throw new RuntimeException();

        ValidatableResponse validacao = response.then();
        validacao.statusCode(201);
    }
}
