package org.example.commerce_site.config;

import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class KeycloakConfig {
	private final KeycloakProperties keycloakProperties;

	@Bean
	public Keycloak keycloak() {
		return KeycloakBuilder.builder()
			.serverUrl(keycloakProperties.getAuthServerUrl())
			.realm(keycloakProperties.getRealm())
			.grantType(OAuth2Constants.CLIENT_CREDENTIALS)
			.clientId(keycloakProperties.getCredentials().getClient())
			.clientSecret(keycloakProperties.getCredentials().getSecret())
			.build();
	}
}
