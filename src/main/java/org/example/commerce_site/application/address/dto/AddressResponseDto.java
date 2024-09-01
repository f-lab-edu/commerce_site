package org.example.commerce_site.application.address.dto;

import org.example.commerce_site.domain.Address;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AddressResponseDto {
	@Getter
	@Builder
	@ToString
	public static class Create {
		private String phoneNumber;
		private String addressType;
		private String postalCode;
		private String roadAddress;
		private String jibunAddress;
		private String buildingName;
		private String addressDetail;

		public static AddressResponseDto.Create of(Address address) {
			return AddressResponseDto.Create.builder()
				.phoneNumber(address.getPhoneNumber())
				.addressType(address.getAddressType())
				.postalCode(address.getPostalCode())
				.roadAddress(address.getRoadAddress())
				.jibunAddress(address.getJibunAddress())
				.buildingName(address.getBuildingName())
				.addressDetail(address.getAddressDetail())
				.build();
		}
	}
}
