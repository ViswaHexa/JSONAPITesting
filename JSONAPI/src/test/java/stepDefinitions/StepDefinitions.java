package stepDefinitions;

import java.io.*;
import io.cucumber.java.en.*;
import io.restassured.filter.log.LogDetail;
import io.restassured.filter.log.ResponseLoggingFilter;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;
import static org.testng.Assert.assertEquals;

public class StepDefinitions {

	private String baseURI, endPoint;
	private Response response;

	@Given("Set base uri {string}")
	public void set_base_uri(String uri) {
		baseURI = uri;
	}

	@When("I Send the GET request with {string}")
	public void i_send_the_get_request_with(String endPoint) throws FileNotFoundException {
		PrintStream file = new PrintStream(new File("Response.log"));
		response = given().baseUri(baseURI).header("Content-Type", "application/json")
				.filter(new ResponseLoggingFilter(LogDetail.BODY, file)).when().get("/" + endPoint);
	}

	@Then("validating the statuscode {int}")
	public void validating_the_statuscode(Integer code) {
		assertEquals(response.getStatusCode(), code);
		System.out.println("Status Code: "+response.getStatusCode()+" ID: "+response.body().jsonPath().get("id"));

	}

	@When("Send the get request {string} and id {int}")
	public void send_the_get_request(String endpoints, Integer id) {
		endPoint = endpoints;
		response = given().baseUri(baseURI).header("Content-Type", "application/json").when()
				.get("/" + endpoints + "/" + id);
		// .then().assertThat().body(JsonSchemaValidator.matchesJsonSchema(new
		// File("src/test/java/TestData/"+endpoint+".json"))).extract().response();

	}

	@Then("validating the statuscode {int} and id {int}")
	public void validating_the_statuscode_and_id(Integer code, Integer id) {
		assertEquals(response.getStatusCode(), code);
		assertEquals(response.getBody().jsonPath().get("id"), id);
		System.out.println(response.getBody().asPrettyString());
	}

	@Then("validate Json Schema")
	public void validate_json_schema() {
		response.then().assertThat().body(JsonSchemaValidator
				.matchesJsonSchema(new File("src/test/java/TestData/Schemas/" + endPoint + "JsonSchema.json")));
	}

	@When("send the post request {string} with body")
	public void send_the_post_request_with_body(String endpoints) {
		response = given().baseUri(baseURI).header("Content-Type", "application/json").body(new File("src/test/java/TestData/PostFiles/"+endpoints+"PostFile.json"))
				.when().post("/" + endpoints);
	}

}
