package com.ssafy.home.user.mapper;

import java.sql.SQLException;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import com.ssafy.home.user.entity.User;

@Mapper
public interface UserMapper {
	User loginUser(Map<String, String> map) throws SQLException;

	void joinUser(User user) throws SQLException;

	void updateUser(User user) throws SQLException;

	void deleteUser(String userId) throws SQLException;
}
