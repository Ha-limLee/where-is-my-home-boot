package com.ssafy.home.user.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.spec.SecretKeySpec;
import javax.xml.bind.DatatypeConverter;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service("jwtService")
public class JwtServiceImpl implements JwtService{
	
	private String secretKey = "asdfa1!r1rf#6zcwmo&1072%4r624rfja;awef24rq][.vrkafr!*ja$2j#via;erfmcqjedzdfq$";
	
	@Override
	public String getToken(String key, Object value) {
		Date expTime = new Date();
		expTime.setTime(expTime.getTime() + 1000 * 60 * 5);
		byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
		Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
		
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("typ", "JWT");
		headerMap.put("alg", "HS256");
		
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		
		JwtBuilder builder = Jwts.builder().setHeader(headerMap)
								.setClaims(map)
								.setExpiration(expTime)
								.signWith(signKey, SignatureAlgorithm.HS256);
		
		return builder.compact();
	}

	@Override
	public Claims getClaims(String token) {
		if (token != null && !"".equals(token)) {
			try {
				byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
				Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
				Claims claims = Jwts.parserBuilder().setSigningKey(signKey).build().parseClaimsJws(token).getBody();
				return claims;
			} catch (ExpiredJwtException e) {
				
			} catch (JwtException e) {
				
			}
		}
		return null;
	}
}
