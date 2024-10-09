package org.example.commerce_site.application.partner.dto;

import org.example.commerce_site.domain.Partner;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class PartnerResponseDto {
	@Builder
	@Getter
	@ToString
	public static class Create {
		private String name;
		private String email;

		public static PartnerResponseDto.Create of(Partner partner) {
			return Create.builder()
				.name(partner.getName())
				.email(partner.getEmail())
				.build();
		}
	}
}
