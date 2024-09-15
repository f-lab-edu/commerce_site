package org.example.commerce_site.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
	@Bean
	public Keycloak keycloak() {
		return KeycloakBuilder.builder()
			.serverUrl("http://localhost:9090")
			.realm("oauth2")
			.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			.clientId("oauth2-client-app")
			.clientSecret("6buMCUOwLIjBhRE6oJ7TNklkIhPqyCl5")
			.build();
	}
}
