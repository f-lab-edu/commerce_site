package org.example.commerce_site.application.address.dto;

import org.example.commerce_site.domain.Address;
import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AddressResponseDto {
	@Getter
	@Builder
	@ToString
	public static class Get {
		private Long id;
		private String phoneNumber;
		private String addressType;
		private String postalCode;
		private String roadAddress;
		private String jibunAddress;
		private String buildingName;
		private String addressDetail;

		public static Get of(Address address) {
			return Get.builder()
				.id(address.getId())
				.phoneNumber(address.getPhoneNumber())
				.addressType(address.getAddressType())
				.postalCode(address.getPostalCode())
				.roadAddress(address.getRoadAddress())
				.jibunAddress(address.getJibunAddress())
				.buildingName(address.getBuildingName())
				.addressDetail(address.getAddressDetail())
				.build();
		}

		public static Page<AddressResponseDto.Get> of(Page<Address> addresses) {
			return addresses.map(AddressResponseDto.Get::of);
		}
	}
}
