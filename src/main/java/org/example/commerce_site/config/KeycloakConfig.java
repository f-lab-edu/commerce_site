package org.example.commerce_site.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KeycloakConfig {
	@Value("${keycloak.realm}")
	private String REALM;

	@Value("${keycloak.auth-server-url}")
	private String AUTH_SERVER_URL;

	@Value("${keycloak.credentials.client}")
	private String CLIENT;

	@Value("${keycloak.credentials.secret}")
	private String CLIENT_SECRET;


	@Bean
	public Keycloak keycloak() {
		return KeycloakBuilder.builder()
			.serverUrl(AUTH_SERVER_URL)
			.realm(REALM)
			.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			.clientId(CLIENT)
			.clientSecret(CLIENT_SECRET)
			.build();
	}
}
