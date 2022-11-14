package com.ssafy.home.user.service;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.mapper.UserMapper;

@Service
public class UserServiceImpl implements UserService {

	private final UserMapper userMapper;
	
	@Autowired
	public UserServiceImpl(UserMapper userMapper) {
		super();
		this.userMapper = userMapper;
	}

	@Override
	public User loginMember(Map<String, String> map) throws Exception {
		for(String s : map.keySet()) {
			System.out.println(s + " : " + map.get(s));
		}
		
		return userMapper.loginUser(map);
	}

	@Override
	public void joinUser(User user) throws Exception {
		userMapper.joinUser(user);
	}

	@Override
	public User getUserInfo(String userId) throws Exception {
		return userMapper.getUserInfo(userId);
	}
	
	@Override
	public void updateUser(User user) throws Exception {
		userMapper.updateUser(user);
	}

	@Override
	public void deleteUser(String userId) throws Exception {
		userMapper.deleteUser(userId);
	}

	@Override
	public List<User> getUsers() throws Exception {
		return userMapper.getUsers();
	}

	@Override
	public void saveRefreshToken(String userId, String refreshToken) {
		userMapper.saveRefreshToken(userId, refreshToken);
	}

	@Override
	public String getRefreshToken(String userId) {
		return userMapper.getRefreshToken(userId);
	}

	@Override
	public void deleteRefreshToken(String userId) {
		userMapper.deleteRefreshToken(userId);
	}

}
