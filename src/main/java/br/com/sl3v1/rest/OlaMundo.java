package br.com.sl3v1.rest;


import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static io.restassured.http.Method.*;

public class OlaMundo {

    public static void main(String[] args){
        Response response = request(GET, "http://restapi.wcaquino.me/ola");
        System.out.println(response.getBody().asString().equals("Ola Mundo!"));
        System.out.println(response.statusCode() == 200);

        ValidatableResponse validacao = response.then();
        validacao.statusCode(200);


    }
}
