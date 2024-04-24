package googlePlaceAPI.googleapi;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class GoogleAddPlace {

	public static void main(String args[]) {
		RestAssured.baseURI = "https://rahulshettyacademy.com/";
		
		//Adding a Place 
		String response = given().log().all().queryParam("key", "qaclick123").body(RawData.getJsonData())
		.when().post("maps/api/place/add/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		JsonPath js = new JsonPath(response);
		String placeId = js.getString("place_id");
		System.out.println(placeId);
		
		//GET the details of a place using ID
		
		String getHttpResponse = given().queryParam("key", "qaclick123").queryParam("place_id", placeId)
		.when().get("maps/api/place/get/json")
		.then().log().all().assertThat().assertThat().statusCode(200).extract().asString();
		
		JsonPath js1 = new JsonPath(getHttpResponse);
		
		String oldaddress = js1.getString("address");
		System.out.println(oldaddress);
		
		//Updating the address using PUT http request
		
		String putHttpResponse = given().log().all().queryParam("key", "qaclick123").body("{\r\n"
				+ "\"place_id\":\""+placeId+"\",\r\n"
				+ "\"address\":\"70 Summer walk, USA\",\r\n"
				+ "\"key\":\"qaclick123\"\r\n"
				+ "}")
		.when().put("maps/api/place/update/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
		given().log().all().queryParam("key", "qaclick123").body("{\r\n"
				+ "    \"place_id\":\""+placeId+"\"\r\n"
				+ "}")
		.when().delete("maps/api/place/delete/json")
		.then().log().all().assertThat().statusCode(200).extract().asString();
		
	}
}
