package org.example.commerce_site.application.shipment;

import org.example.commerce_site.application.order.OrderDetailService;
import org.example.commerce_site.application.order.OrderService;
import org.example.commerce_site.application.partner.PartnerService;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.shipment.dto.ShipmentRequestDto;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.OrderDetail;
import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.domain.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentFacade {
	private final OrderService orderService;
	private final OrderDetailService orderDetailService;
	private final ShipmentService shipmentService;
	private final PartnerService partnerService;
	private final ProductService productService;

	@Transactional
	public void updateTrackingCode(ShipmentRequestDto.UpdateTrackingNumber dto) {
		OrderDetail orderDetail = orderDetailService.getOrderDetail(dto.getOrderDetailId());
		if (orderDetail.getOrder().getStatus() == OrderStatus.CANCELLED) {
			throw new CustomException(ErrorCode.ORDER_CAN_NOT_UPDATE_TRACK_CODE);
		}

		Product product = productService.getProduct(orderDetail.getProductId());
		Partner partner = partnerService.getPartner(dto.getUserAuthId());
		if (product.getPartnerId() != partner.getId()) {
			throw new CustomException(ErrorCode.ACCESS_DENIED);
		}

		shipmentService.updateTrackingCode(orderDetail.getId(), dto.getTrackingNumber());
		orderService.updateStatus(orderDetail.getOrder(), OrderStatus.SHIPPED);
	}
}
