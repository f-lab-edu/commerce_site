package org.example.commerce_site.application.shipment;

import java.util.List;

import org.example.commerce_site.application.order.dto.OrderDetailRequestDto;
import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.attribute.ShipmentStatus;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.Shipment;
import org.example.commerce_site.infrastructure.shipment.ShipmentBulkRepository;
import org.example.commerce_site.infrastructure.shipment.ShipmentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {
	private final ShipmentRepository shipmentRepository;
	private final ShipmentBulkRepository shipmentBulkRepository;

	@Transactional
	public void createShipment(Order order, List<OrderDetailResponseDto.Get> orderDetailList, Address address) {
		shipmentBulkRepository.saveAll(orderDetailList.stream().map(orderDetail -> Shipment.builder()
			.order(order)
			.orderDetail(OrderDetailRequestDto.Create.toEntity(orderDetail, order))
			.address(address)
			.status(ShipmentStatus.PENDING)
			.build()).toList());
	}

	@Transactional
	public void updateTrackingCode(Long orderDetailId, String trackingNumber) {
		Shipment shipment = shipmentRepository.findByOrderDetailId(orderDetailId).orElseThrow(
			() -> new CustomException(ErrorCode.SHIPMENT_NOT_FOUND)
		);
		shipment.updateTrackingNumber(trackingNumber);
		shipment.updateStatus(ShipmentStatus.SHIPPED);
		shipmentRepository.save(shipment);
	}
}
