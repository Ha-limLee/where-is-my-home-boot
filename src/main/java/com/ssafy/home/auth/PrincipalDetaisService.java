package com.ssafy.home.auth;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ssafy.home.user.entity.User;
import com.ssafy.home.user.repository.UserRepository;

import lombok.RequiredArgsConstructor;

// http://localhost:8080/login => 여기서 동작을 안한다.
@Service
@RequiredArgsConstructor
public class PrincipalDetaisService implements UserDetailsService {

	private final UserRepository userRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		System.out.println("PrincipalDetailsService의 loadUserByUsername()");
		User userEntity = userRepository.findByUserId(username);
		System.out.println("userEntity:" + userEntity);
		
		if(userEntity != null)
			return new PrincipalDetails(userEntity);
		else
			return null;
	}
	
	
}
