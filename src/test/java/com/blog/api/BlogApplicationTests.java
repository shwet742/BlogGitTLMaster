package com.blog.api;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.blog.api.repositories.UserRepo;

@SpringBootTest
class BlogApplicationTests {

	@Autowired
	private UserRepo userRepo;
	
	
	@Test
	void contextLoads() {
	}

	@Test
	public void repoTest()
	{
		String className=this.userRepo.getClass().getName();
		String pkg=this.userRepo.getClass().getPackageName();
		System.out.println(className);
		System.out.println(pkg);
	}
}
