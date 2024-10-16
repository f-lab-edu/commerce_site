package org.example.commerce_site.application.address.dto;

import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.User;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AddressRequestDto {
	@Getter
	@Builder
	@ToString
	public static class Create {
		private String userAuthId;
		private String phoneNumber;
		private String addressType;
		private String postalCode;
		private String roadAddress;
		private String jibunAddress;
		private String roadNameCode;
		private String buildingName;
		private String addressDetail;
		private Boolean isPrimary;

		public static Address toEntity(AddressRequestDto.Create dto, User user) {
			return Address.builder()
				.userId(user)
				.phoneNumber(dto.getPhoneNumber())
				.addressType(dto.getAddressType())
				.postalCode(dto.getPostalCode())
				.roadAddress(dto.getRoadAddress())
				.jibunAddress(dto.getJibunAddress())
				.buildingName(dto.getBuildingName())
				.addressDetail(dto.getAddressDetail())
				.isPrimary(dto.getIsPrimary())
				.build();
		}
	}
}
