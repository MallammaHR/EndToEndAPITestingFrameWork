package com.qa.api.gorest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class DeleteUserTest  extends BaseTest{
	
	@Test
	public void updatUserTest()
	//1.create User---POST
	{	//Uselombok
		//User user =new User("Mallu",StringUtils.getRandomEmailId(),"female","active");
		
		User userlombok = User.builder()
							  .name("Rahul")
							  .email(StringUtils.getRandomEmailId())
							  .gender("male")
							  .status("inactive").build();
		
		
		Response responsePOST = restClient.
				post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userlombok, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(responsePOST.jsonPath().getString("name"), "Rahul");
		Assert.assertNotNull(responsePOST.jsonPath().getString("id"));
		
		//fetch the User-ID
		String userId = responsePOST.jsonPath().getString("id");
		System.out.println("User-ID=====>"+userId);
		
		
		//2.Get the user using the same User-ID__GET
		Response responseGET = restClient.
				get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("OK"));
		Assert.assertEquals(responseGET.jsonPath().getString("id"), userId);
		
		
		//3.try to get the response the using the same userID-GET
		
		Response responseDELETE = restClient.delete(
		        BASE_URL_GOREST,
		        GOREST_USERS_ENDPOINT + "/" + userId,
		        null,
		        null,
		        AuthType.BEARER_TOKEN,
		        ContentType.JSON
		);

		// validate DELETE response
		Assert.assertEquals(responseDELETE.statusCode(), 204);
		Assert.assertTrue(responseDELETE.statusLine().contains("No Content"));
		
		//4.get response and verify user deleted or not
		 responseGET = restClient.
				get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userId, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(responseGET.statusLine().contains("Not Found"));
		Assert.assertEquals(responseGET.statusCode(), 404);
		Assert.assertEquals(responseGET.jsonPath().getString("message"), "Resource not found");
	}

	

}
