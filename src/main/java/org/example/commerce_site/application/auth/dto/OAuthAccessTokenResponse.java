package org.example.commerce_site.application.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OAuthAccessTokenResponse {
	@Getter
	@Setter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
	public static class Keycloak {
		private String accessToken;
		private int expiresIn;
		private int refreshExpiresIn;
		private String refreshToken;
		private String tokenType;
		private String idToken;
		private int notBeforePolicy;
		private String sessionState;
		private String scope;
	}
}
