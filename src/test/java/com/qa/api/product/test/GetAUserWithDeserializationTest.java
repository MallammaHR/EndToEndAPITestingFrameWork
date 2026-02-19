package com.qa.api.product.test;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.JsonUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetAUserWithDeserializationTest extends BaseTest {
	private String tokenID;
	@BeforeClass
	public void setUpToke() {
		tokenID = "79d05a46c5b014beae89f278c36831871a0271f0331bb5776de639ed9a449954";
		ConfigManager.set("bearertoken", tokenID);
	}
	
	
	@Test
	public void createUserTest() {
		
	
		User user=new User(null,"Dhanayatha", StringUtils.getRandomEmailId(), "female", "active");
		
	Response responsePOST  = 	restClient.post(BASE_URL_GOREST, 
											GOREST_USERS_ENDPOINT, 
											user, 
											null,
											null, 
											AuthType.BEARER_TOKEN,
											ContentType.JSON);
	Assert.assertEquals(responsePOST.jsonPath().getString("name"), "Dhanayatha");
	Assert.assertNotNull(responsePOST.jsonPath().getString("id"));
	
	//get the user-ID
	String userID=responsePOST.jsonPath().getString("id");
	
	
	Response responseGET = restClient.get(BASE_URL_GOREST, 
										GOREST_USERS_ENDPOINT+"/"+userID, 
										null, 
										null,
										AuthType.BEARER_TOKEN, 
										ContentType.JSON);
	Assert.assertTrue(responseGET.statusLine().contains("OK"));
	//Assert.assertEquals(responseGET.jsonPath().getString("id"), userID);
	
	User userResponse = JsonUtils.deserialize(responseGET, User.class);
	Assert.assertEquals(userResponse.getName(), user.getName());
	
	
	}

}
