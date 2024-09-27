package org.example.commerce_site.application.auth;

import org.example.commerce_site.application.auth.dto.OAuthAccessTokenResponse;
import org.example.commerce_site.config.KeycloakProperties;
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
	private final KeycloakProperties keycloakProperties;

	public OAuthAccessTokenResponse.Keycloak getAccessToken(String code) {
		MultiValueMap<String, String> info = new LinkedMultiValueMap<>();
		info.add("grant_type", keycloakProperties.getGrantType());
		info.add("client_id", keycloakProperties.getCredentials().getClient());
		info.add("client_secret", keycloakProperties.getCredentials().getSecret());
		info.add("redirect_uri", keycloakProperties.getUri().getRedirect());
		info.add("code", code);

		final HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

		final HttpEntity<MultiValueMap<String, String>> httpEntity = new HttpEntity<>(info, headers);

		return restTemplate.postForEntity(keycloakProperties.getUri().getToken(), httpEntity,
			OAuthAccessTokenResponse.Keycloak.class).getBody();
	}
}
