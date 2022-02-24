package br.com.sl3v1.rest;

import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static io.restassured.RestAssured.*;
import static io.restassured.http.Method.GET;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

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
    public void devoConhecerOutrasFormasRestAssured() {
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

    @Test
    public void devoConhecerMatchersHamcrest() {
        assertThat("Maria", is("Maria"));
        assertThat(128, is(128));
        assertThat(128, isA(Integer.class));
        assertThat(12.8, isA(Double.class));
        assertThat(12.8, greaterThan(11.5));
        assertThat(12.8, lessThan(13.5));

        List<Integer> impares = Arrays.asList(1, 3, 5, 7, 9, 11, 13, 15, 17, 19);
        assertThat(impares, hasSize(10));
        assertThat(impares, contains(1, 3, 5, 7, 9, 11, 13, 15, 17, 19)); //Deve estar ordenado e ter todos os itens
        assertThat(impares, containsInAnyOrder(1, 5, 3, 15, 9, 13, 11, 17, 7, 19));
        assertThat(impares, hasItem(19));
        assertThat(impares, hasItems(19, 7, 1));

        assertThat("Maria", is(not("João")));
        assertThat("Maria", not("João"));
        assertThat("Mary", anyOf(is("Marina"), is("Mary")));
        assertThat("Jolibedina", allOf(startsWith("Jo"), endsWith("na"), containsString("be")));

    }

    @Test
    public void devoValidarOBody() {
        given()
        .when()
                .get("http://restapi.wcaquino.me/ola")
        .then()
                .assertThat()
                .statusCode(200)
                .body(is(notNullValue()))
                .body(containsStringIgnoringCase("mundo"))
                .body(is("Ola Mundo!"))
        ;
    }

}
