package org.example.commerce_site.representation.user.request;

import org.example.commerce_site.application.user.dto.UserRequestDto;

import lombok.Getter;
import lombok.ToString;

public class UserRequest {
	@Getter
	@ToString
	public static class Create {
		private String id;
		private String userName;
		private String email;

		public static UserRequestDto.Create toDTO(UserRequest.Create request) {
			return UserRequestDto.Create.builder()
				.id(request.getId())
				.name(request.getUserName())
				.email(request.getEmail())
				.build();
		}
	}
}
