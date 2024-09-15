package org.example.commerce_site.application.user.dto;

import org.example.commerce_site.attribute.UserStatus;
import org.example.commerce_site.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserRequestDto {
	@Getter
	@Builder
	@ToString
	public static class Create {
		private String name;
		private String email;
		private String password;

		public static User toEntity(UserRequestDto.Create dto, String authId) {
			return User.builder()
				.authId(authId)
				.name(dto.getName())
				.email(dto.getEmail())
				.status(UserStatus.ACTIVE)
				.build();
		}
	}
}
