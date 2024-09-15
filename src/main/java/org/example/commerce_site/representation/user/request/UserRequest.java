package org.example.commerce_site.representation.user.request;

import org.example.commerce_site.application.user.dto.UserRequestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

public class UserRequest {
	@Getter
	@ToString
	public static class Create {
		@NotBlank
		private String name;

		@NotBlank
		@Email(message = "이메일 형식이 올바르지 않습니다.")
		private String email;

		@NotBlank
		@Size(min = 8, max = 20, message = "비밀번호는 8자리 이상 20자리 이하이어야 합니다.")
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$",
			message = "비밀번호는 영문자와 숫자를 포함해야 합니다.")
		private String password;

		public static UserRequestDto.Create toDTO(UserRequest.Create request) {
			return UserRequestDto.Create.builder()
				.name(request.getName())
				.email(request.getEmail())
				.password(request.getPassword())
				.build();
		}
	}
}
