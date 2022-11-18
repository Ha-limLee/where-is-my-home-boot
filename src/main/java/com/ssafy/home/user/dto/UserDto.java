package com.ssafy.home.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto {
	private String userId;
	private String userName;
	private String userPassword;
	private String address;
	private String phoneNumber;
	private String authority;
	private String token;
}
