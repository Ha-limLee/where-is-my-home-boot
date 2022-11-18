package com.ssafy.home.user.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssafy.home.user.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String>{

	Optional<User> findByUserId(String userId);
	Optional<User> findByUserName(String userName);
}
