package com.ssafy.home.user.service;

import java.util.Date;

import io.jsonwebtoken.Claims;

public interface JwtService {
	String createToken(String key, Object value, String subject, long expTime);	
	Claims checkToken(String token);
	String createRefreshToken(String key, Object data);
	String createAccessToken(String key, Object data);
}
