package org.example.commerce_site.application.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;
import org.example.commerce_site.infrastructure.order.OrderDetailBulkRepository;
import org.example.commerce_site.infrastructure.order.OrderDetailRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderDetailServiceTest {

	@InjectMocks
	private OrderDetailService orderDetailService;

	@Mock
	private OrderDetailBulkRepository orderDetailBulkRepository;

	@Mock
	private OrderDetailRepository orderDetailRepository;

	@Test
	void create_ShouldCreateOrderDetails() {
		OrderRequestDto.CreateDetail detailDto1 = OrderRequestDto.CreateDetail.builder()
			.productId(1L).quantity(2L).unitPrice(BigDecimal.TEN).build();
		OrderRequestDto.CreateDetail detailDto2 = OrderRequestDto.CreateDetail.builder()
			.productId(2L).quantity(3L).unitPrice(BigDecimal.ONE).build();

		List<OrderRequestDto.CreateDetail> detailDtos = List.of(detailDto1, detailDto2);
		Order order = Order.builder().id(10L).build();

		List<OrderDetail> orderDetails = detailDtos.stream()
			.map(dto -> OrderRequestDto.CreateDetail.toEntity(dto, order))
			.toList();
		List<OrderDetailResponseDto.Get> orderDetailDtoList = orderDetails.stream()
			.map(OrderDetailResponseDto.Get::toDto)
			.toList();

		orderDetailService.createOrderDetails(detailDtos, order);

		verify(orderDetailBulkRepository).saveAll(argThat(argument ->
				argument.size() == orderDetails.size() &&
					argument.stream().allMatch(od ->
						od.getProductId() != null &&
							od.getQuantity() != null &&
							od.getUnitPrice() != null)),
			eq(order.getId())
		);
	}

	@Test
	public void get_OrderDetails() {
		OrderDetailResponseDto.Get orderDetailResponseDto = OrderDetailResponseDto.Get.builder()
			.id(1L).orderId(1L).productId(1L).quantity(2L).unitPrice(BigDecimal.TEN)
			.build();
		List<OrderDetailResponseDto.Get> orderDetails = List.of(orderDetailResponseDto);

		OrderDetail orderDetail = OrderDetail.builder()
			.id(1L).order(Order.builder().id(1L).build())
			.productId(1L).quantity(2L).unitPrice(BigDecimal.TEN).build();
		when(orderDetailRepository.findAllByOrderId(any(Long.class))).thenReturn(List.of(orderDetail));

		List<OrderDetailResponseDto.Get> result = orderDetailService.getOrderDetails(1L);

		// Then
		verify(orderDetailRepository).findAllByOrderId(any(Long.class));
		assertEquals(1, result.size());
	}

	@Test
	public void get_OrderDetail_Success() {
		OrderDetail orderDetail = OrderDetail.builder()
			.id(1L).order(Order.builder().id(1L).build())
			.productId(1L).quantity(2L).unitPrice(BigDecimal.TEN).build();

		when(orderDetailRepository.findById(any(Long.class))).thenReturn(Optional.of(orderDetail));

		OrderDetail result = orderDetailService.getOrderDetail(1L);

		verify(orderDetailRepository).findById(any(Long.class));
		assertNotNull(result);
	}

	@Test
	public void get_OrderDetail_OrderDetailNotFound() {
		when(orderDetailRepository.findById(any(Long.class))).thenReturn(Optional.empty());

		CustomException exception = assertThrows(CustomException.class, () -> {
			orderDetailService.getOrderDetail(1L);
		});
		assertEquals(ErrorCode.ORDER_DETAIL_NOT_FOUND, exception.getErrorCode());
	}
}