package org.example.commerce_site.application.user;

import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.user.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {
	private final UserRepository userRepository;

	@Transactional(readOnly = true)
	public User getUser(Long userId) {
		return userRepository.findById(userId).orElseThrow(
			() -> new CustomException(ErrorCode.USER_NOT_FOUND)
		);
	}

	@Transactional
	public void create(UserRequestDto.Create dto) {
		userRepository.save(UserRequestDto.Create.toEntity(dto));
	}
}
