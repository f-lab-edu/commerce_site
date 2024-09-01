package org.example.commerce_site.application.user;

import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.application.user.dto.UserResponseDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.UserRepository;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	public UserResponseDto.Create create(UserRequestDto.Create dto) {
		// TODO : email 중복 체크
		return UserResponseDto.Create.of(userRepository.save(UserRequestDto.Create.toEntity(dto)));
	}

	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
	}
}
