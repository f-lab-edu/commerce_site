package org.example.commerce_site.application.auth;

import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {
	private final Keycloak keycloak;
	private final UserService userService;
	@Value("${keycloak.realm}")
	private String REALM_NAME;
	private final RealmResource realmResource = keycloak.realm(REALM_NAME);
	private final UsersResource usersResource = realmResource.users();

	@Transactional
	public String createUser(UserRequestDto.Create dto) {
		UserRepresentation user = new UserRepresentation();
		user.setUsername(dto.getEmail());
		user.setEnabled(true);

		Response response = usersResource.create(user);

		if (response.getStatus() == 201) {
			String userId = CreatedResponseUtil.getCreatedId(response);

			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setTemporary(false);
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			passwordCred.setValue(dto.getPassword());
			UserResource userResource = usersResource.get(userId);
			userResource.resetPassword(passwordCred);

			return userId;
		} else {
			throw new CustomException(ErrorCode.ADD_USER_ERROR);
		}
	}

	public void deleteUser(String keyCloakId) {
		usersResource.get(keyCloakId).remove();
	}
}
