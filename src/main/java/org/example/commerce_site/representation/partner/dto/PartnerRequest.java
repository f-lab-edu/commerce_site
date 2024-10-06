package org.example.commerce_site.representation.partner.dto;

import org.example.commerce_site.application.partner.dto.PartnerRequestDto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.ToString;

public class PartnerRequest {
	@Getter
	@ToString
	public static class Create {
		@NotBlank
		private String firstName;

		@NotBlank
		private String lastName;

		@NotBlank
		private String shopName;

		@NotBlank
		@Email(message = "유효한 이메일 주소를 입력하세요.")
		private String email;

		@NotBlank
		@Pattern(regexp = "^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$",
			message = "패스워드는 8자리 이상 20자리 이하 영문자와 숫자를 포함해야 합니다.")
		private String password;

		@NotBlank
		private String businessNumber;

		public static PartnerRequestDto.Create toDTO(PartnerRequest.Create request) {
			return PartnerRequestDto.Create.builder()
				.firstName(request.getFirstName())
				.lastName(request.getLastName())
				.email(request.getEmail())
				.shopName(request.getShopName())
				.password(request.getPassword())
				.businessNumber(request.getBusinessNumber())
				.build();
		}
	}

	@Getter
	@ToString
	public static class CreateWebHook {
		private String id;
		private String name;
		private String email;
		private String bizId;

		public static PartnerRequestDto.CreateWebHook toDTO(PartnerRequest.CreateWebHook request) {
			return PartnerRequestDto.CreateWebHook.builder()
				.id(request.getId())
				.name(request.getName())
				.email(request.getEmail())
				.bizId(request.getBizId())
				.build();
		}
	}

}
