package org.example.commerce_site.application.user;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Optional;

import org.example.commerce_site.application.user.dto.UserRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
	@Mock
	private UserRepository userRepository;

	@InjectMocks
	private UserService userService;

	private UserRequestDto.Create userRequestDto = UserRequestDto.Create.builder().build();
	private User user = User.builder().authId("testAuthId").build();

	@Test
	public void getUser_UserNotFound() {
		when(userRepository.findByAuthId(any())).thenReturn(Optional.empty());

		CustomException exception = assertThrows(CustomException.class, () -> {
			userService.getUser("testAuthId");
		});

		assertEquals(ErrorCode.USER_NOT_FOUND, exception.getErrorCode());
	}

	@Test
	public void createUser_shouldCreateUser() {
		when(userRepository.save(any())).thenReturn(user);

		userService.create(userRequestDto);

		verify(userRepository).save(any());
	}
}