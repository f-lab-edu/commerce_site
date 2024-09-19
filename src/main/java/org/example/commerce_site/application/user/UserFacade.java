package org.example.commerce_site.application.user;

import org.example.commerce_site.application.auth.AuthService;
import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.application.user.dto.UserResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserFacade {
	private final UserService userService;
	private final AuthService authService;

	@Transactional
	public UserResponseDto.Create createUser(UserRequestDto.Create dto) {
		userService.checkEmailDuplicated(dto.getEmail());
		String keyCloakId = null;
		try {
			keyCloakId = authService.createUser(dto);
		} catch (Exception e) {
			log.error("Keycloak 사용자 생성 실패: {}", e.getMessage());
			throw new CustomException(ErrorCode.CREATE_KEYCLOAK_USER_ERROR);
		}
		UserResponseDto.Create result = null;
		try {
			result = userService.create(dto, keyCloakId);
		} catch (Exception e) {
			log.error("User DB 저장 실패, Keycloak 유저 삭제 진행: {}", keyCloakId);
			try {
				authService.deleteUser(keyCloakId);
			} catch (Exception deleteException) {
				log.error("Keycloak 사용자 삭제 실패: {}", deleteException.getMessage());
			}
			throw new CustomException(ErrorCode.ADD_USER_ERROR);
		}

		return result;
	}
}