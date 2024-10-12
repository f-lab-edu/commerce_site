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
		private String firstName;
		private String lastName;
		private String email;
		private String shopName;
		private String password;
		private String businessNumber;
	}

	@Getter
	@Builder
	@ToString
	public static class CreateWebHook {
		private String id;
		private String name;
		private String email;
		private String bizId;

		public static Partner toEntity(PartnerRequestDto.CreateWebHook dto) {
			return Partner.builder()
				.name(dto.getName())
				.authId(dto.getId())
				.email(dto.getEmail())
				.businessNumber(dto.getBizId())
				.status(PartnerStatus.ACTIVE)
				.build();
		}
	}
}
