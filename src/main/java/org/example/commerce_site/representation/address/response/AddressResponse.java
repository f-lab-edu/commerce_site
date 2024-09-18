package org.example.commerce_site.representation.address.response;

import org.example.commerce_site.application.address.dto.AddressResponseDto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AddressResponse {
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

		public static AddressResponse.Create of(AddressResponseDto.Create dto) {
			return AddressResponse.Create.builder()
				.phoneNumber(dto.getPhoneNumber())
				.addressType(dto.getAddressType())
				.postalCode(dto.getPostalCode())
				.roadAddress(dto.getRoadAddress())
				.jibunAddress(dto.getJibunAddress())
				.buildingName(dto.getBuildingName())
				.addressDetail(dto.getAddressDetail())
				.build();
		}
	}
}
