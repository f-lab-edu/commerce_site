package org.example.commerce_site.application.auth;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.commerce_site.application.auth.dto.OAuthAccessTokenResponse;
import org.example.commerce_site.config.KeycloakProperties;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

@ExtendWith(MockitoExtension.class)
class KeycloakAuthServiceTest {
	@Mock
	private RestTemplate restTemplate;

	@Mock
	private Keycloak keycloak;

	@Mock
	private KeycloakProperties keycloakProperties;

	@Mock
	private KeycloakProperties.Credentials credentials;

	@Mock
	private KeycloakProperties.Uri uri;

	@Test
	void getAccessToken_ShouldReturnAccessToken() {
		String code = "validCode";
		OAuthAccessTokenResponse.Keycloak expectedToken = new OAuthAccessTokenResponse.Keycloak();
		expectedToken.setAccessToken("sampleToken");
		expectedToken.setRefreshToken("sampleRefreshToken");
		expectedToken.setIdToken("sampleIdToken");

		when(credentials.getClient()).thenReturn("clientId");
		when(credentials.getSecret()).thenReturn("clientSecret");

		when(uri.getRedirect()).thenReturn("redirectUri");
		when(uri.getToken()).thenReturn("tokenUri");

		when(keycloakProperties.getCredentials()).thenReturn(credentials);
		when(keycloakProperties.getUri()).thenReturn(uri);

		when(restTemplate.postForEntity(eq("tokenUri"), any(), eq(OAuthAccessTokenResponse.Keycloak.class)))
			.thenReturn(ResponseEntity.ok(expectedToken));

		KeycloakAuthService keycloakAuthService = new KeycloakAuthService(keycloak, keycloakProperties, restTemplate);

		OAuthAccessTokenResponse.Keycloak result = keycloakAuthService.getAccessToken(code);

		assertNotNull(result);
		assertEquals(expectedToken.getAccessToken(), result.getAccessToken());
		assertEquals(expectedToken.getRefreshToken(), result.getRefreshToken());
		assertEquals(expectedToken.getIdToken(), result.getIdToken());
	}
}