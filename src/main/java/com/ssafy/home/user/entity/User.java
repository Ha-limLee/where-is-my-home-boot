package com.ssafy.home.user.entity;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="member")
public class User {
	
	@Id
	private String userId;
	
	private String userName;
	private String userPassword;
	private String address;
	private String phoneNumber;
	private String role;
	private String token;
	
	public List<String> getRoleList() {
		if(this.role.length() > 0) {
			return Arrays.asList(this.role.split(","));
		}
		return new ArrayList<>();
	}
}
