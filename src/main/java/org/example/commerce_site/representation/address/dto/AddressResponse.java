package org.example.commerce_site.representation.address.dto;

import org.example.commerce_site.application.address.dto.AddressResponseDto;
import org.springframework.data.domain.Page;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class AddressResponse {
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

		public static Get of(AddressResponseDto.Get dto) {
			return Get.builder()
				.id(dto.getId())
				.phoneNumber(dto.getPhoneNumber())
				.addressType(dto.getAddressType())
				.postalCode(dto.getPostalCode())
				.roadAddress(dto.getRoadAddress())
				.jibunAddress(dto.getJibunAddress())
				.buildingName(dto.getBuildingName())
				.addressDetail(dto.getAddressDetail())
				.build();
		}

		public static Page<Get> of(Page<AddressResponseDto.Get> dtos) {
			return dtos.map(Get::of);
		}
	}
}
