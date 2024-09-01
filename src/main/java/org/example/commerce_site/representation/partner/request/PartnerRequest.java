package org.example.commerce_site.representation.partner.request;

import org.example.commerce_site.application.partner.dto.PartnerRequestDto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.ToString;

public class PartnerRequest {
	@Getter
	@ToString
	public static class Create {
		@NotBlank
		private String name;
		@NotBlank
		//TODO email 형식 체크
		private String email;
		@NotBlank
		//TODO 패스워드 형식 체크 (8자리 이상 20자리 이하 영문 + 숫자)
		private String password;
		@NotBlank
		private String businessNumber;

		public static PartnerRequestDto.Create toDTO(PartnerRequest.Create request) {
			return PartnerRequestDto.Create.builder()
				.name(request.getName())
				.email(request.getEmail())
				//TODO PWD 암호화
				.password(request.getPassword())
				.businessNumber(request.getBusinessNumber())
				.build();
		}
	}
}
