package com.ssafy.home.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.home.user.entity.User;

public interface UserRepository extends JpaRepository<User, String>{

	User findByUserId(String userId);
	User findByUserName(String userName);
}
