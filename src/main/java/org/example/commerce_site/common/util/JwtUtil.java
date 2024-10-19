package org.example.commerce_site.common.util;

import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtException;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {
	private final NimbusJwtDecoder jwtDecoder;

	public Jwt decodeToken(String token) {
		try {
			return jwtDecoder.decode(token);
		} catch (JwtException e) {
			log.error(e.getMessage());
			throw new JwtException(e.getMessage());
		}
	}
}
