package com.ssafy.home.user.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class User {
	
	private String userId;
	private String userName;
	private String userPassword;
	private String address;
	private String phoneNumber;
	private String authority;
	
}
