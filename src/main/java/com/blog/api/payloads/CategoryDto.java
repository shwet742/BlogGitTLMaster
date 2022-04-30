package com.blog.api.payloads;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {
	
	
	private Integer categoryId;
	
	@NotEmpty
	@Size(min=4,message = "min size of category title is 4") 
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10,message = "min size of category desc is 10")
	private String categoryDescription;
	
	

}
