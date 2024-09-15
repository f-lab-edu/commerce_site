package org.example.commerce_site.application.user;

import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.application.user.dto.UserResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.user.UserRepository;
import org.keycloak.admin.client.CreatedResponseUtil;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UserResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;
	private final Keycloak keycloak;

	@Transactional
	public UserResponseDto.Create create(UserRequestDto.Create dto) {
		// 1. email 중복체크를 한다.
		// 2. 키클락 서버에 가입 요청을 보낸다.
		// 3. 가입 성공하면 비밀번호 재설정과 롤 부여
		// 4. User DB 에는 이메일과 키클락 서버에 저장된 userID 만 저장한다.

		UserRepresentation user = new UserRepresentation();
		user.setEmail(dto.getEmail());
		user.setUsername(dto.getName());

		RealmResource realmResource = keycloak.realm("oauth2");
		UsersResource usersResource = realmResource.users();

		Response response = usersResource.create(user);
		if(response.getStatus() == 201) {
			String userId = CreatedResponseUtil.getCreatedId(response);

			// create password credential
			CredentialRepresentation passwordCred = new CredentialRepresentation();
			passwordCred.setTemporary(false);
			passwordCred.setType(CredentialRepresentation.PASSWORD);
			passwordCred.setValue(dto.getPassword());
			log.info("Created userId {}", userId);
			UserResource userResource = usersResource.get(userId);

			// Set password credential
			userResource.resetPassword(passwordCred);
		}

		// TODO : email 중복 체크
		return UserResponseDto.Create.of(userRepository.save(UserRequestDto.Create.toEntity(dto)));
	}

	@Transactional(readOnly = true)
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
	}
}
