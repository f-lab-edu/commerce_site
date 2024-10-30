package org.example.commerce_site.application.shipment.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;

public class ShipmentRequestDto {
	@Getter
	@Builder
	@ToString
	public static class UpdateTrackingNumber {
		private Long orderDetailId;
		private String userAuthId;
		private String trackingNumber;
	}

}
