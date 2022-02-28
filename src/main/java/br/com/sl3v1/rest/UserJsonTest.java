package br.com.sl3v1.rest;

import io.restassured.http.Method;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Test;

import java.util.Arrays;

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
                .body("age", greaterThan(28))
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
    public void deveVerificarSegundonivel() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users/2")
                .then()
                .statusCode(200)
                .body("name", containsString("Joaquina"))
                .body("endereco.rua", is("Rua dos bobos"))
                .body("endereco.numero", greaterThan(-1))
        ;
    }

    @Test
    public void deveVerificarLista() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users/3")
                .then()
                .statusCode(200)
                .body("name", containsString("Ana"))
                .body("filhos", hasSize(2))
                .body("filhos[0].name", is("Zezinho"))
                .body("filhos[1].name", is("Luizinho"))
                .body("filhos.name", hasSize(2))
                .body("filhos.name", hasItem("Zezinho"))
                .body("filhos.name", hasItems("Zezinho", "Luizinho"))
        ;
    }

    @Test
    public void deveRetornarErroUsuarioInexistente() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users/4")
                .then()
                .statusCode(404)
                .body("error", is("Usuário inexistente"))
        ;
    }

    @Test
    public void deveVerificarListaRaiz() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("name", hasItems("João da Silva", "Maria Joaquina", "Ana Júlia"))
                .body("endereco[1].rua", is("Rua dos bobos"))
                .body("age[1]", greaterThanOrEqualTo(25))
                .body("filhos.name", hasItems(Arrays.asList("Zezinho", "Luizinho")))
                .body("salary", contains(1234.5678f, 2500, null))
        ;
    }

    @Test
    public void devoFazerVerificacoeAvancadas() {
        given()
                .when()
                .get("https://restapi.wcaquino.me/users")
                .then()
                .statusCode(200)
                .body("$", hasSize(3))
                .body("age.findAll{it <= 25}.size()", is(2))
                .body("age.findAll{it <= 25 && it > 20}.size()", is(1))
                .body("findAll{it.age <= 25 && it.age > 20}.name", hasItem("Maria Joaquina"))
                .body("findAll{it.age <= 25}[0].name", is("Maria Joaquina"))
                .body("findAll{it.age <= 25}[-1].name", is("Ana Júlia"))
                .body("find{it.age <= 25}.name", is("Maria Joaquina"))
                .body("findAll{it.name.contains('o')}.name", hasItems("Maria Joaquina", "João da Silva"))
                .body("findAll{it.name.length() < 10}.name", hasItem("Ana Júlia"))
                .body("name.collect{it.toUpperCase()}", hasItem("MARIA JOAQUINA"))
                .body("name.findAll{it.startsWith('Ana')}.collect{it.toUpperCase()}", hasItem("ANA JÚLIA"))
                .body("name.findAll{it.startsWith('Ana')}.collect{it.toUpperCase()}.toArray()", allOf(arrayContaining("ANA JÚLIA"), arrayWithSize(1)))
                .body("age.collect{it * 3}", hasItems(90, 75, 60))
                .body("id.max()", is(3))
                .body("salary.min()", is(1234.5678f))
                .body("salary.findAll{it!=null}.sum()", is(closeTo(3734.5678f,0.001)))
                .body("salary.findAll{it!=null}.sum()", allOf(greaterThan(3000d), lessThan(5000d)))
        ;
    }
}
