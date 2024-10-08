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
		private String id;
		private String name;
		private String email;

		public static User toEntity(UserRequestDto.Create dto) {
			return User.builder()
				.authId(dto.getId())
				.name(dto.getName())
				.email(dto.getEmail())
				.status(UserStatus.ACTIVE)
				.build();
		}
	}
}
