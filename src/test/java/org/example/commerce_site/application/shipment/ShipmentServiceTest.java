package org.example.commerce_site.application.shipment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.util.List;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.attribute.ShipmentStatus;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.Shipment;
import org.example.commerce_site.infrastructure.shipment.ShipmentBulkRepository;
import org.example.commerce_site.infrastructure.shipment.ShipmentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShipmentServiceTest {
	@Mock
	private ShipmentRepository shipmentRepository;

	@Mock
	private ShipmentBulkRepository shipmentBulkRepository;

	@InjectMocks
	private ShipmentService shipmentService;

	private Order order = Order.builder().id(1L).build();;
	private Address address =  Address.builder().id(1L).build();;
	private List<OrderDetailResponseDto.Get> orderDetailList = List.of(
		OrderDetailResponseDto.Get.builder().productId(1L).orderId(1L).build(),
		OrderDetailResponseDto.Get.builder().productId(2L).orderId(1L).build()
	);

	@Test
	void createShipment_ShouldCreateShipments_WhenValidData() {
		doNothing().when(shipmentBulkRepository).saveAll(any());

		shipmentService.createShipment(order,orderDetailList, address);

		verify(shipmentBulkRepository).saveAll(any());
	}

	@Test
	void updateTrackingCode_ShouldThrowException_WhenShipmentNotFound() {
		Long orderDetailId = 1L;
		String trackingNumber = "tracking123";

		when(shipmentRepository.findByOrderDetailId(orderDetailId)).thenReturn(java.util.Optional.empty());

		// Act & Assert
		CustomException exception = assertThrows(CustomException.class, () -> {
			shipmentService.updateTrackingCode(orderDetailId, trackingNumber);
		});
		assertEquals(ErrorCode.SHIPMENT_NOT_FOUND, exception.getErrorCode());
	}

	@Test
	void updateTrackingCode_ShouldUpdateTrackingCode_WhenShipmentExists() {
		Long orderDetailId = 1L;
		String trackingNumber = "tracking123";
		Shipment shipment = mock(Shipment.class);

		when(shipmentRepository.findByOrderDetailId(orderDetailId)).thenReturn(java.util.Optional.of(shipment));

		shipmentService.updateTrackingCode(orderDetailId, trackingNumber);

		verify(shipment).updateTrackingNumber(trackingNumber);
		verify(shipment).updateStatus(ShipmentStatus.SHIPPED);
		verify(shipmentRepository).save(shipment);
	}
}