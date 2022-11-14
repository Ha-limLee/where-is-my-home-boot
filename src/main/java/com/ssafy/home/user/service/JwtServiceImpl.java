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
	private final long ACCESS_EXP_TIME = 1000 * 60 * 60 * 2; // 2시간
	private final long REFRESH_EXP_TIME = 1000 * 60 * 60 * 24 * 7; // 1주일
	
	@Override
	public String createToken(String key, Object value, String subject, long expTime) {
		Date expDate = new Date();
		expDate.setTime(expDate.getTime() + expTime);
		
		byte[] secretByteKey = DatatypeConverter.parseBase64Binary(secretKey);
		Key signKey = new SecretKeySpec(secretByteKey, SignatureAlgorithm.HS256.getJcaName());
		
		Map<String, Object> headerMap = new HashMap<>();
		headerMap.put("typ", "JWT");
		headerMap.put("alg", "HS256");
		
		Map<String, Object> map = new HashMap<>();
		map.put(key, value);
		
		JwtBuilder builder = Jwts.builder().setHeader(headerMap)
								.setClaims(map)
								.setExpiration(expDate)
								.signWith(signKey, SignatureAlgorithm.HS256);
		
		return builder.compact();
	}

	@Override
	public Claims checkToken(String token) {
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

	@Override
	public String createRefreshToken(String key, Object data) {
		// TODO Auto-generated method stub
		return createToken(key, data, "refresh-token", REFRESH_EXP_TIME);
	}

	@Override
	public String createAccessToken(String key, Object data) {
		return createToken(key, data, "access-token", ACCESS_EXP_TIME);
	}
}
