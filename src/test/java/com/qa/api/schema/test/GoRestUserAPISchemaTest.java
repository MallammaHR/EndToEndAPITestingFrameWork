package com.qa.api.schema.test;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.qa.api.base.BaseTest;
import com.qa.api.constants.AuthType;
import com.qa.api.manager.ConfigManager;
import com.qa.api.pojo.User;
import com.qa.api.utils.SchemaValidator;
import com.qa.api.utils.StringUtils;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GoRestUserAPISchemaTest extends BaseTest{
	
	@Test
	public void getUserAPISchemaTest() {
		
		ConfigManager.set("bearertoken","ef3a7ddaa9f69852a071447d94d8bad5a14350fe1f7221875b69688012982160");
		
		Response response = restClient.get(BASE_URL_GOREST,
					GOREST_USERS_ENDPOINT, 
					null, 
					null,  
					AuthType.BEARER_TOKEN,
					ContentType.ANY);
		
		Assert.assertTrue(SchemaValidator.validateSchema(response,"schema/getuserschema.json"));

	}
	
	
	@Test
	public void creatAUserAPISchemaTest() {
		
		ConfigManager.set("bearertoken","ef3a7ddaa9f69852a071447d94d8bad5a14350fe1f7221875b69688012982160");
		
		User user= User.builder()
		 .name("API Schema Validation")
		 .status("active")
		 .email(StringUtils.getRandomEmailId())
		 .gender("female")
		 .build();
		
		Response response =restClient.post(BASE_URL_GOREST, 
						GOREST_USERS_ENDPOINT,
						user,
						null,
						null,
						AuthType.BEARER_TOKEN,
						ContentType.JSON
						);
		Assert.assertTrue(SchemaValidator.validateSchema(response,"schema/createuserschema.json"));

	}
}

