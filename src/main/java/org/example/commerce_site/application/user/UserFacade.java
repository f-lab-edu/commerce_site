package org.example.commerce_site.application.user;

import org.example.commerce_site.application.auth.AuthService;
import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.application.user.dto.UserResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserFacade {
	private final UserService userService;
	private final AuthService authService;

	@Transactional
	public UserResponseDto.Create createUser(UserRequestDto.Create dto) {
		userService.checkEmailDuplicated(dto.getEmail());
		String keyCloakId = authService.createUser(dto);
		return userService.create(dto, keyCloakId);
	}
}
