package com.ssafy.home.user.service;

import java.util.List;
import java.util.Map;

import com.ssafy.home.user.entity.User;

public interface UserService {
	User loginMember(Map<String, String> map) throws Exception;

	void joinUser(User user) throws Exception;

	User getUserInfo(String userId) throws Exception;
	
	void updateUser(User user) throws Exception;

	void deleteUser(String userId) throws Exception;

	List<User> getUsers() throws Exception;

	void saveRefreshToken(String userId, String refreshToken);

	String getRefreshToken(String userId);

	void deleteRefreshToken(String userId);
}
