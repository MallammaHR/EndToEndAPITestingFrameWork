package com.qa.api.gorest.test;

import java.io.File;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AppConstants;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.ExcelUtils;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;


public class CreateUserTest extends BaseTest
{
	private String tokenID;
	
	@BeforeClass
	public void setUpToken() {
		 tokenID = "ef3a7ddaa9f69852a071447d94d8bad5a14350fe1f7221875b69688012982160";
		ConfigManager.set("bearertoken",tokenID);
	}
	
	@DataProvider
	public Object[][] getUserData()
	{
		
		return new Object[][]{
			
			{"Mallu","female","active"},
			{"Ranjith","male","inactive"},
			{"Tom","male","active"}
		};
	}
	
	@DataProvider
	public Object[][] getUserExcelData() {
		return ExcelUtils.readData(AppConstants.CREATE_USER_SHEET_NAME);
	}

	@Test(dataProvider = "getUserExcelData")
	public void creatUserTestWithRandomEmailID(String name,String gender,String status) {
		
		User user =new User(null,name,StringUtils.getRandomEmailId(),gender,status);
		
		
		Response response =restClient.
				post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT,user, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
			response.prettyPrint();
			Assert.assertEquals(response.jsonPath().getString("name"), name);
			Assert.assertEquals(response.jsonPath().getString("gender"), gender);
			Assert.assertEquals(response.jsonPath().getString("status"), status);
			Assert.assertNotNull(response.jsonPath().getString("id"));	
	}
	
	
	//with complete user json passing in the body getRandomEmailId
	@Test(enabled =false)
	public void createAUserTestWithJsonString() {

		String userJson = "{\n"
				+ "\"name\": \"Roopa\",\n"
				+ "\"email\": \"roopa090@gmail.com\",\n"
				+ "\"gender\": \"male\",\n"
				+ "\"status\": \"active\"\n"
				+ "}";
		
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userJson, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), "Roopa");
		Assert.assertNotNull(response.jsonPath().getString("id"));	
		System.out.println("*****************************************************************************");
	}
//	
//	//json body but with random emailID-method from the String utils
	@Test(enabled =false)
	 public void createAUserTestWithJsonStringWithRandomEmailID() {
		 String emailId = StringUtils.getRandomEmailId();
		 String userJSON = "{\r\n"
					+ "    \"name\": \"MalluMani14\",\r\n"
					+ "    \"email\": \""+emailId+"\",\r\n"
					+ "    \"gender\": \"male\",\r\n"
					+ "    \"status\": \"active\"\r\n"
					+ "}";
		Response response = restClient.post(BASE_URL_GOREST,GOREST_USERS_ENDPOINT, userJSON,  null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), "MalluMani14");
		Assert.assertNotNull(response.jsonPath().getString("id"));
		System.out.println("*****************************************************************************");
	 }


	
//	//user.josn sending in the form of File
	@Test(enabled =false)
	public void createAUserTestWithJsonfile() {

		File userFile = new File("./src/test/resources/jsons/user.json");
		Response response = restClient.post(BASE_URL_GOREST, GOREST_USERS_ENDPOINT, userFile, null, null, AuthType.BEARER_TOKEN, ContentType.JSON);
		Assert.assertEquals(response.jsonPath().getString("name"), "Teena");
		Assert.assertNotNull(response.jsonPath().getString("id"));	
		System.out.println("*****************************************************************************");
	}
//	
}
