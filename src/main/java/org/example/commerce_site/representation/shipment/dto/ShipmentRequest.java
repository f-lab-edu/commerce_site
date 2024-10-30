package org.example.commerce_site.representation.shipment.dto;

import org.example.commerce_site.application.shipment.dto.ShipmentRequestDto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.ToString;

public class ShipmentRequest {
	@Getter
	@ToString
	public static class UpdateTrackingNumber {
		@NotNull
		private String trackingNumber;

		public static ShipmentRequestDto.UpdateTrackingNumber toDto(ShipmentRequest.UpdateTrackingNumber request,
			String userAuthId, Long orderDetailId) {
			return ShipmentRequestDto.UpdateTrackingNumber.builder()
				.trackingNumber(request.trackingNumber)
				.userAuthId(userAuthId)
				.orderDetailId(orderDetailId)
				.build();
		}
	}
}
