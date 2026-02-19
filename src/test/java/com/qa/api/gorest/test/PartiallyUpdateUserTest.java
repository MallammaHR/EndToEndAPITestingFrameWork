package com.qa.api.gorest.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.pojo.User;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class PartiallyUpdateUserTest extends BaseTest {
	@Test
	public void creatAUserTestUsingFileSystem() 
	//1.create User---POST
	//User user =new User("Mallu",StringUtils.getRandomEmailId(),"female","active");
	{

		//create a user -POST
		User user=User.builder()
					.name("TONNY")
					.gender("male")
					.email(StringUtils.getRandomEmailId())
					.status("active").build();
		
		Response postResponse = restClient.post(
									BASE_URL_GOREST, 
									GOREST_USERS_ENDPOINT, 
									user, 
									null, 
									null, 
									AuthType.BEARER_TOKEN, 
									ContentType.JSON);
		Assert.assertEquals(postResponse.jsonPath().getString("name"),"TONNY");
		Assert.assertNotNull(postResponse.jsonPath().getString("id"));
		
		String userID=postResponse.jsonPath().getString("id");
		System.out.println("The user-ID is : "+userID);
		
		//get the user for the same user-ID
		Response getResponse = restClient.get(
						            BASE_URL_GOREST, 
									GOREST_USERS_ENDPOINT+"/"+userID,
									null, 
									null,
									AuthType.BEARER_TOKEN, 
									ContentType.JSON);
		Assert.assertEquals(getResponse.jsonPath().getString("id"), userID);
		//update the same user
		user.setName("TONNY autoation");
		user.setStatus("inactive");
		Response putResponse = restClient.patch(
									BASE_URL_GOREST, 
									GOREST_USERS_ENDPOINT+"/"+userID,
									user, 
									null,
									null,
									AuthType.BEARER_TOKEN,
									ContentType.JSON);
		Assert.assertTrue(putResponse.statusLine().contains("OK"));
		Assert.assertEquals(putResponse.jsonPath().getString("name"),"TONNY autoation");
		Assert.assertEquals(putResponse.jsonPath().getString("status"), "inactive");
								
		//get the same user 
		 restClient.get(
	            BASE_URL_GOREST, 
				GOREST_USERS_ENDPOINT+"/"+userID,
				null, 
				null,
				AuthType.BEARER_TOKEN, 
				ContentType.JSON);
		Assert.assertEquals(getResponse.jsonPath().getString("id"), userID);
	
		

	}
	
}
