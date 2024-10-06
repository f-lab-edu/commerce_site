package org.example.commerce_site.application.auth;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.example.commerce_site.application.auth.dto.OAuthAccessTokenResponse;
import org.example.commerce_site.application.partner.dto.PartnerRequestDto;
import org.example.commerce_site.attribute.UserRoles;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.config.KeycloakProperties;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.ClientsResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.ClientRepresentation;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class KeycloakAuthService {
	private final Keycloak keycloak;
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

		RestTemplate restTemplate = new RestTemplate();
		return restTemplate.postForEntity(keycloakProperties.getUri().getToken(), httpEntity,
			OAuthAccessTokenResponse.Keycloak.class).getBody();
	}

	public void createPartner(PartnerRequestDto.Create requestDto) {
		UserRepresentation user = createUserRepresentation(requestDto);
		UsersResource usersResource = keycloak.realm(keycloakProperties.getRealm()).users();
		String userId = null;
		try (Response response = usersResource.create(user)) {
			if (response.getStatus() == 201) {
				userId = response.getLocation().getPath().replaceAll(".*/([^/]+)$", "$1");

				resetUserPassword(userId, requestDto.getPassword());
				assignClientRoleToUser(userId, keycloakProperties.getCredentials().getClient(),
					UserRoles.ROLE_PARTNER.name());
			} else {
				throw new CustomException(ErrorCode.CREATE_KEYCLOAK_USER_ERROR);
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CustomException(ErrorCode.CREATE_KEYCLOAK_USER_ERROR);
		}
	}

	private UserRepresentation createUserRepresentation(PartnerRequestDto.Create partner) {
		UserRepresentation user = new UserRepresentation();
		user.setUsername(partner.getEmail());
		user.setFirstName(partner.getFirstName());
		user.setLastName(partner.getLastName());
		user.setEmail(partner.getEmail());
		user.setEnabled(true);

		Map<String, List<String>> resourceAccess = new HashMap<>();
		resourceAccess.put("bizId", Collections.singletonList(partner.getBusinessNumber()));
		resourceAccess.put("shopName", Collections.singletonList(partner.getShopName()));
		user.setAttributes(resourceAccess);

		return user;
	}

	private void resetUserPassword(String userId, String password) {
		CredentialRepresentation credential = new CredentialRepresentation();
		credential.setTemporary(false);
		credential.setType(CredentialRepresentation.PASSWORD);
		credential.setValue(password);

		UsersResource usersResource = keycloak.realm(keycloakProperties.getRealm()).users();
		usersResource.get(userId).resetPassword(credential);
	}

	private void assignClientRoleToUser(String userId, String clientId, String roleName) {
		ClientsResource clientsResource = keycloak.realm(keycloakProperties.getRealm()).clients();
		ClientRepresentation oauth2Client = clientsResource.findByClientId(clientId).getFirst();

		if (oauth2Client == null) {
			throw new CustomException(ErrorCode.CREATE_KEYCLOAK_USER_ERROR);
		}

		RoleRepresentation userClientRole = keycloak.realm(keycloakProperties.getRealm())
			.clients().get(oauth2Client.getId())
			.roles().get(roleName).toRepresentation();

		UserResource userResource = keycloak.realm(keycloakProperties.getRealm()).users().get(userId);
		userResource.roles()
			.clientLevel(oauth2Client.getId())
			.add(List.of(userClientRole));
	}
}
