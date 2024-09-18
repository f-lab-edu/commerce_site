package org.example.commerce_site.representation.address.request;

import org.example.commerce_site.application.address.dto.AddressRequestDto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.ToString;

public class AddressRequest {
	@Getter
	@ToString
	public static class Create {
		@NotNull(message = "User ID cannot be null")
		//TODO 토큰에서 가져오는 것으로 수정
		private Long userId;

		@NotBlank(message = "Phone number cannot be blank")
		@Pattern(regexp = "^01[0-9]-\\d{4}-\\d{4}$", message = "Phone number must be 10-11 digits")
		private String phoneNumber;

		@Size(max = 30, message = "Address type can have a maximum of 30 characters")
		private String addressType;

		@NotBlank(message = "Postal code cannot be blank")
		@Size(max = 10, message = "Postal code can have a maximum of 10 characters")
		private String postalCode;

		@NotBlank(message = "Road address cannot be blank")
		@Size(max = 255, message = "Road address can have a maximum of 255 characters")
		private String roadAddress;

		@Size(max = 255, message = "Jibun address can have a maximum of 255 characters")
		private String jibunAddress;

		@Size(max = 13, message = "Road name code can have a maximum of 13 characters")
		private String roadNameCode;

		@Size(max = 255, message = "Building name can have a maximum of 255 characters")
		private String buildingName;

		@Size(max = 255, message = "Address detail can have a maximum of 255 characters")
		private String addressDetail;

		private Boolean isPrimary;

		public static AddressRequestDto.Create toDto(AddressRequest.Create request) {
			return AddressRequestDto.Create.builder()
				.userId(request.getUserId())
				.phoneNumber(request.getPhoneNumber())
				.addressType(request.getAddressType())
				.postalCode(request.getPostalCode())
				.roadAddress(request.getRoadAddress())
				.jibunAddress(request.getJibunAddress())
				.roadNameCode(request.getRoadNameCode())
				.buildingName(request.getBuildingName())
				.addressDetail(request.getAddressDetail())
				.isPrimary(request.getIsPrimary())
				.build();
		}
	}
}
