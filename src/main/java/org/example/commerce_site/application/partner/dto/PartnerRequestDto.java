package org.example.commerce_site.application.partner.dto;

import org.example.commerce_site.attribute.PartnerStatus;
import org.example.commerce_site.domain.Partner;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class PartnerRequestDto {
	@Getter
	@Builder
	@ToString
	public static class Create {
		private String name;
		private String email;
		private String password;
		private String businessNumber;

		public static Partner toEntity(PartnerRequestDto.Create dto) {
			return Partner.builder()
				.name(dto.getName())
				.email(dto.getEmail())
				.password(dto.getPassword())
				.status(PartnerStatus.ACTIVE)
				.businessNumber(dto.getBusinessNumber())
				.build();
		}
	}
}
