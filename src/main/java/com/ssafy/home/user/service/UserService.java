package com.ssafy.home.user.service;

import java.util.Map;

import com.ssafy.home.user.entity.User;

public interface UserService {
	User loginMember(Map<String, String> map) throws Exception;

	void joinUser(User user) throws Exception;

	void updateUser(User user) throws Exception;
}
