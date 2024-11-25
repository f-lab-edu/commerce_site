package org.example.commerce_site.application.shipment;

import static org.mockito.Mockito.*;

import org.example.commerce_site.application.order.OrderDetailService;
import org.example.commerce_site.application.order.OrderService;
import org.example.commerce_site.application.partner.PartnerService;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.shipment.dto.ShipmentRequestDto;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;
import org.example.commerce_site.domain.Partner;
import org.example.commerce_site.domain.Product;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ShipmentFacadeTest {
	@InjectMocks
	private ShipmentFacade shipmentFacade;

	@Mock
	private OrderService orderService;

	@Mock
	private OrderDetailService orderDetailService;

	@Mock
	private ShipmentService shipmentService;

	@Mock
	private PartnerService partnerService;

	@Mock
	private ProductService productService;


	@Test
	void updateTrackingCode_ShouldUpdateTrackingCodeAndOrderStatus_WhenConditionsAreMet() {
		ShipmentRequestDto.UpdateTrackingNumber dto =
			ShipmentRequestDto.UpdateTrackingNumber
				.builder()
				.orderDetailId(1L)
				.trackingNumber("trackingNumber")
				.userAuthId("userAuthId")
				.build();

		OrderDetail orderDetail = mock(OrderDetail.class);
		when(orderDetail.getId()).thenReturn(1L);
		when(orderDetail.getProductId()).thenReturn(2L);
		when(orderDetail.getOrder()).thenReturn(mock(Order.class));
		when(orderDetail.getOrder().getStatus()).thenReturn(OrderStatus.PENDING);

		Product product = mock(Product.class);
		when(product.getPartnerId()).thenReturn(3L);

		Partner partner = mock(Partner.class);
		when(partner.getId()).thenReturn(3L);

		when(orderDetailService.getOrderDetail(dto.getOrderDetailId())).thenReturn(orderDetail);
		when(productService.getProduct(orderDetail.getProductId())).thenReturn(product);
		when(partnerService.getPartner(dto.getUserAuthId())).thenReturn(partner);

		shipmentFacade.updateTrackingCode(dto);

		verify(shipmentService).updateTrackingCode(orderDetail.getId(), dto.getTrackingNumber());
		verify(orderService).updateStatus(orderDetail.getOrder(), OrderStatus.SHIPPED);
	}

	@Test
	void updateTrackingCode_ShouldThrowException_WhenOrderStatusIsCancelled() {
		ShipmentRequestDto.UpdateTrackingNumber dto =
			ShipmentRequestDto.UpdateTrackingNumber
				.builder()
				.orderDetailId(1L)
				.trackingNumber("trackingNumber")
				.userAuthId("userAuthId")
				.build();

		Order order = Order.builder().status(OrderStatus.CANCELLED).build();
		OrderDetail orderDetail = OrderDetail.builder().order(order).build();

		when(orderDetailService.getOrderDetail(dto.getOrderDetailId())).thenReturn(orderDetail);

		CustomException exception = Assertions.assertThrows(
			CustomException.class,
			() -> shipmentFacade.updateTrackingCode(dto)
		);

		Assertions.assertEquals(ErrorCode.ORDER_CAN_NOT_UPDATE_TRACK_CODE, exception.getErrorCode());
	}

	@Test
	void updateTrackingCode_ShouldThrowException_WhenAccessDenied() {
		ShipmentRequestDto.UpdateTrackingNumber dto =
			ShipmentRequestDto.UpdateTrackingNumber
				.builder()
				.orderDetailId(1L)
				.trackingNumber("trackingNumber")
				.userAuthId("userAuthId")
				.build();

		Product product = Product.builder().partnerId(3L).build();
		Partner partner = Partner.builder().id(4L).build();
		Order order = Order.builder().status(OrderStatus.CONFIRMED).build();
		OrderDetail orderDetail = OrderDetail.builder().order(order).productId(1L).build();

		when(orderDetailService.getOrderDetail(dto.getOrderDetailId())).thenReturn(orderDetail);
		when(productService.getProduct(orderDetail.getProductId())).thenReturn(product);
		when(partnerService.getPartner(dto.getUserAuthId())).thenReturn(partner);

		Assertions.assertThrows(CustomException.class, () -> shipmentFacade.updateTrackingCode(dto));
	}
}