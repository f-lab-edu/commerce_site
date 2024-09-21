package org.example.commerce_site.application.auth;

import org.example.commerce_site.application.auth.dto.OAuthAccessTokenResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class KeycloakAuthService {
	private final RestTemplate restTemplate = new RestTemplate();
	@Value("${oauth.keycloak.grant-type}")
	private String GRANT_TYPE;
	@Value("${oauth.keycloak.credentials.client}")
	private String CLIENT_ID;
	@Value("${oauth.keycloak.credentials.secret}")
	private String CLIENT_SECRET;
	@Value("${oauth.keycloak.uri.redirect}")
	private String REDIRECT_URI;
	@Value("${oauth.keycloak.uri.token}")
	private String TOKEN_URI;

	public OAuthAccessTokenResponse.Keycloak getAccessToken(String code) {
		MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
		info.add("grant_type", GRANT_TYPE);
		info.add("client_id", CLIENT_ID);
		info.add("client_secret", CLIENT_SECRET);
		info.add("redirect_uri", REDIRECT_URI);
		info.add("code", code);

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(info, headers);

		return restTemplate.postForEntity(TOKEN_URI, httpEntity, OAuthAccessTokenResponse.Keycloak.class).getBody();
	}
}
