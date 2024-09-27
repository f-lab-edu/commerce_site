package org.example.commerce_site.application.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.List;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;
import org.example.commerce_site.infrastructure.order.OrderDetailBulkRepository;
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

	@Test
	void testCreateOrderDetails() {
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

		when(orderDetailBulkRepository.saveAll(anyList(), eq(order.getId()))).thenReturn(
			orderDetailDtoList
		);

		List<OrderDetailResponseDto.Get> result = orderDetailService.createOrderDetails(detailDtos, order);

		verify(orderDetailBulkRepository).saveAll(argThat(argument ->
				argument.size() == orderDetails.size() &&
					argument.stream().allMatch(od ->
						od.getProductId() != null &&
							od.getQuantity() != null &&
							od.getUnitPrice() != null)),
			eq(order.getId())
		);
		
		assertEquals(detailDtos.size(), result.size());
	}
}