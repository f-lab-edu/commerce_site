package org.example.commerce_site.representation.shipment;

import org.example.commerce_site.application.shipment.ShipmentFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.shipment.dto.ShipmentRequest;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_PARTNER')")
@RequestMapping("/shipments")
public class ShipmentController {
	private final ShipmentFacade shipmentFacade;

	@PutMapping("/{order_detail_id}")
	public ApiSuccessResponse updateShipment(
		@PathVariable("order_detail_id") Long orderDetailId,
		@RequestAttribute("userId") String userAuthId,
		@RequestBody ShipmentRequest.UpdateTrackingNumber request
	) {
		shipmentFacade.updateTrackingCode(
			ShipmentRequest.UpdateTrackingNumber.toDto(request, userAuthId, orderDetailId));
		return ApiSuccessResponse.success();
	}
}
