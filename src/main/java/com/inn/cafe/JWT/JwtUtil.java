package com.inn.cafe.JWT;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Service
public class JwtUtil {
	
	private String secret = "S9H65BhwbDswe_$F";
	
	public String extractUsername(String token) {
		return extractClaims(token, Claims::getSubject);
	}
	//Check if the token is expired (validation is neccessary date)
	public Date extractExpiration(String token) {
		return extractClaims(token , Claims::getExpiration);
	}
	
	public <T> T extractClaims(String token, Function<Claims, T> claimsResolver) {
		final Claims claims = extractAllClaims(token);
		return claimsResolver.apply(claims);
	}
	public Claims extractAllClaims(String token) {
		return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
	}
	private Boolean isTokenExpired(String token) {
		return extractExpiration(token).before(new Date());
	}
	public String generateToken(String username, String role) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("role", role);
		return createToken(claims, username);
		
	}
	private String createToken(Map<String, Object> claims, String subject) {
		return Jwts.builder() //Create JWT
				.setClaims(claims) //passed the Claims
				.setSubject(subject) //passed the Subject
				.setIssuedAt(new Date(System.currentTimeMillis())) // Current Date
				.setExpiration(new Date(System.currentTimeMillis() + 1000*60*60*10)) //Expiration Date
				.signWith(SignatureAlgorithm.HS256, secret).compact(); //Signed Algo + return all as String
	}
	
	
	public Boolean validateToken(String token, UserDetails userDetails) {
		final String username = extractUsername(token);
		return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
		
	}

}
