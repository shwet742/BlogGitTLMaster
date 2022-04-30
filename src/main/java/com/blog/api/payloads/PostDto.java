package com.blog.api.payloads;

import java.util.Date;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import com.blog.api.entity.Category;
import com.blog.api.entity.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	
	private Integer postId;
	
	@NotEmpty
	@Size(min=4,message = "min size of post title is 4") 
	private String title;
	
	@NotEmpty
	@Size(min=4,message = "min size of post content is 10") 
	private String content;
	
	private String imageName;
	
	private Date addedDate;
	
	private UserDto user;
	
	private CategoryDto category;
	
	


}
