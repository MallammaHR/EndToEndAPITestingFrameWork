package com.qa.api.pojo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(Include.NON_NULL)//3
public class User 
{
	//1.
	private Integer id;
	private String name;
	private String email;
	private String gender;
	private String status;
//	
	//2
//	public User(String name, String email, String gender, String status) {
//		super();
//		this.name = name;
//		this.email = email;
//		this.gender = gender;
//		this.status = status;
//	}
	
	
}
