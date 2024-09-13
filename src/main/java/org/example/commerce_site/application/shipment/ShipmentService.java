package org.example.commerce_site.application.shipment;

import java.util.List;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.attribute.ShipmentStatus;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.Shipment;
import org.example.commerce_site.infrastructure.shipment.ShipmentBulkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ShipmentService {
	private final ShipmentBulkRepository shipmentBulkRepository;

	@Transactional
	public void createShipment(Order order, List<OrderDetailResponseDto.Get> orderDetailList, Address address) {
		shipmentBulkRepository.saveAll(orderDetailList.stream().map(orderDetail -> Shipment.builder()
			.order(order)
			.orderDetail(OrderDetailResponseDto.Get.toEntity(orderDetail, order))
			.address(address)
			.status(ShipmentStatus.PENDING)
			.build()).toList());
	}
}
