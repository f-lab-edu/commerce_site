package org.example.commerce_site.application.auth.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

public class OAuthAccessTokenResponse {
	@Getter
	@Setter
	@NoArgsConstructor
	@JsonIgnoreProperties(ignoreUnknown = true)
	public static class Keycloak {
		@JsonProperty("access_token")
		private String accessToken;

		@JsonProperty("expires_in")
		private int expiresIn;

		@JsonProperty("refresh_expires_in")
		private int refreshExpiresIn;

		@JsonProperty("refresh_token")
		private String refreshToken;

		@JsonProperty("token_type")
		private String tokenType;

		@JsonProperty("id_token")
		private String idToken;

		@JsonProperty("not-before-policy")
		private int notBeforePolicy;

		@JsonProperty("session_state")
		private String sessionState;

		@JsonProperty("scope")
		private String scope;
	}
}
