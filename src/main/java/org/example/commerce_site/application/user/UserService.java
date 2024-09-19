package org.example.commerce_site.application.user;

import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.application.user.dto.UserResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.user.UserRepository;
import org.keycloak.admin.client.CreatedResponseUtil;
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

	@Transactional(readOnly = true)
	public void checkEmailDuplicated(String email) {
		// 1. email 중복체크
		userRepository.findByEmail(email).ifPresent(
			user -> {
				throw new CustomException(ErrorCode.EMAIL_ALREADY_EXISTED);
			}
		);
	}

	@Transactional
	public UserResponseDto.Create create(UserRequestDto.Create dto, String userId) throws Exception {
		return UserResponseDto.Create.of(userRepository.save(UserRequestDto.Create.toEntity(dto, userId)));
	}

	@Transactional(readOnly = true)
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
	}
}
