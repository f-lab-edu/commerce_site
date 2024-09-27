package org.example.commerce_site.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "oauth.keycloak")
public class KeycloakProperties {

	private String realm;
	private String authServerUrl;
	private Credentials credentials;
	private String grantType;
	private Uri uri;
	private String apiKey;

	@Getter
	@Setter
	public static class Credentials {
		private String client;
		private String secret;
	}

	@Getter
	@Setter
	public static class Uri {
		private String redirect;
		private String token;
	}
}