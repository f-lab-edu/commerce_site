package org.example.commerce_site.application.user.dto;

import org.example.commerce_site.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class UserResponseDto {
	@Builder
	@Getter
	@ToString
	public static class Create {
		private String name;
		private String email;

		public static UserResponseDto.Create of(User user) {
			return Create.builder()
				.name(user.getName())
				.email(user.getEmail())
				.build();
		}
	}
}
