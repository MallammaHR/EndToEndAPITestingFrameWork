package com.qa.api.pojo;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.Builder;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Products 
{
	private Integer id;
	private String title;
	private Double price;
	private String category;
	private String description;
	private String image;
	private Rating rating;
	 
	@Data
	@Builder
	@AllArgsConstructor
	@NoArgsConstructor
	public static class Rating{
		private Double rate;
		private Integer count;
	}


}
