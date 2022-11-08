package com.ssafy.home.user.mapper;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.ssafy.home.user.entity.User;

@Mapper
public interface UserMapper {
	User loginUser(Map<String, String> map) throws SQLException;

	void joinUser(User user) throws SQLException;

	void updateUser(User user) throws SQLException;

	void deleteUser(String userId) throws SQLException;

	User getUserInfo(String userId) throws SQLException;

	List<User> getUsers() throws SQLException;
}
