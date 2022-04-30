package com.blog.api.payloads;
//data transfer

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.sun.istack.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class UserDto {
	
	private int id;
	
	@NotEmpty
	@Size(min=4,message = "username must be min of 4 characters")
    private String name;
	
	@Email(message = "Email Address is not valid!!")
	private String email;
	
	@NotEmpty
	@Size(min=3,max=10,message = "password must be min of 3 chars and max of 3 chars")
	private String password;
	
	@NotEmpty
	private String about;
	

}
