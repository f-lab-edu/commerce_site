package org.example.commerce_site.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;

@Configuration
public class JwtDecoderConfig {
	@Bean
	public NimbusJwtDecoder jwtDecoder() {
		String jwkSetUri = "http://localhost:9090/realms/oauth2/protocol/openid-connect/certs";
		return NimbusJwtDecoder.withJwkSetUri(jwkSetUri).build();
	}
}
