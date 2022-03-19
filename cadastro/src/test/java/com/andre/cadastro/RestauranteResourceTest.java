package com.andre.cadastro;

import static io.restassured.RestAssured.given;

import javax.ws.rs.core.Response.Status;


import com.andre.cadastro.Restaurante.AtualizarRestauranteDTO;
import com.andre.cadastro.Restaurante.Restaurante;
import org.approvaltests.Approvals;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import com.github.database.rider.cdi.api.DBRider;
import com.github.database.rider.core.api.configuration.DBUnit;
import com.github.database.rider.core.api.configuration.Orthography;
import com.github.database.rider.core.api.dataset.DataSet;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;


@DBRider
@DBUnit(caseInsensitiveStrategy = Orthography.LOWERCASE)
@QuarkusTest
@QuarkusTestResource(CadastroTestLifecyclingManager.class)
public class RestauranteResourceTest {

//	@Test
//	@DataSet("restaurantes-cenario-1.yml")
//	public void testBuscarRestaurantes() {
//		String resultado = given()
//				.when().get("/restaurantes")
//				.then()
//				.statusCode(200)
//				.extract().asString();
//		Approvals.verifyJson(resultado);
//	}

//	@Test
//	@DataSet("restaurantes-cenario-1.yml")
//	public void testAdicionarRestaurante() {
//		LocalizacaoDTO localizacao = new LocalizacaoDTO();
//		localizacao.latitude = 233.4;
//		localizacao.longitude = 4456.8;
//		AdicionarRestauranteDTO dto = new AdicionarRestauranteDTO();
//		dto.nomeFantasia = "novoNome";
//		dto.proprietario ="Andre";
//		dto.cnpj = "09687967455";
//		dto.localizacao = localizacao;
//
//		given()
//				.with()
//				.body(dto)
//				.when().post("/restaurantes")
//				.then()
//				.statusCode(201)
//				.extract().asString();
//	}
//
//	private RequestSpecification given() {
//		return RestAssured.given().contentType(ContentType.JSON);
//	}
//
//	@Test
//	@DataSet("restaurantes-cenario-1.yml")
//	public void testAlterarRestaurante() {
//		AtualizarRestauranteDTO dto = new AtualizarRestauranteDTO();
//		dto.nomeFantasia = "novoNome";
//		Long parameterValue = 123L;
//		given()
//			.with().pathParam("id", parameterValue)
//			.body(dto)
//			.when().put("/restaurantes/{id}")
//			.then()
//			.statusCode(Status.NO_CONTENT.getStatusCode())
//			.extract().asString();
//
//		Restaurante restaurante = Restaurante.findById(parameterValue);
//		Assertions.assertEquals(dto.nomeFantasia, restaurante.getNome());
//	}
//
//	@Test
//	@DataSet("restaurantes-cenario-1.yml")
//	public void testDeletarRestaurante() {
//		Long parameterValue = 123L;
//		given()
//				.with().pathParam("id", parameterValue)
//				.when().delete("/restaurantes/{id}")
//				.then()
//				.statusCode(Status.NO_CONTENT.getStatusCode())
//				.extract().asString();
//
//	}
}
