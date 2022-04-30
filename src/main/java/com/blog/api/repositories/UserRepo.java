package com.blog.api.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.blog.api.entity.User;


public interface UserRepo extends JpaRepository<User, Integer> {
	
	
	

}
