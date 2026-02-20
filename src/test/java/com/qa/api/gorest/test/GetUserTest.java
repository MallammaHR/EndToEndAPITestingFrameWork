	package com.qa.api.gorest.test;

import java.util.HashMap;
import java.util.Map;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.aventstack.chaintest.plugins.ChainTestListener;
import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;

import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import io.qameta.allure.Severity;
import io.qameta.allure.SeverityLevel;
import io.qameta.allure.Story;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

@Epic("Epic 100 : Go Rest Get User API Feature")
@Story("US 100 : feature go rest api -  get user api")
public class GetUserTest extends BaseTest{
	
	private String tokenID;
	@BeforeClass
	public void setUpToken() {
		 tokenID = "ef3a7ddaa9f69852a071447d94d8bad5a14350fe1f7221875b69688012982160";
		ConfigManager.set("bearertoken",tokenID);
	}
	@Description("getting all the users.....")
	@Owner("Mallamma Reddy")
	@Severity(SeverityLevel.CRITICAL)
	@Test
	public void getAllUsersTest()
	{
		 ChainTestListener.log("Get- all users api Test");
		Response response = restClient.
		get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Test(enabled=false)
	public void getAllUsersWithQueryParamTest()
	{
		Map<String,String> queryParams = new HashMap<String, String>();
		queryParams.put("name", "mallu");
		queryParams.put("status","active");
		Response response = restClient.
			get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, null,queryParams, AuthType.BEARER_TOKEN, ContentType.JSON);
		
		Assert.assertTrue(response.statusLine().contains("OK"));
	}
	
	@Test
	public void singleUserTest()
	{
		String userID ="8363859";
		
		Response response = restClient.
				get(BASE_URL_GOREST, GOREST_USERS_ENDPOINT+"/"+userID, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		System.out.println("Response :"+response);
		Assert.assertEquals(response.jsonPath().getString("id"), userID);
		Assert.assertTrue(response.statusLine().contains("OK"));
	}

}
