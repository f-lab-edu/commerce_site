package org.example.commerce_site.application.order;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.infrastructure.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Test
	void create_ShouldCreateOrder() {
		List<OrderRequestDto.CreateDetail> details = new ArrayList<>();
		OrderRequestDto.CreateDetail detailDto1 = OrderRequestDto.CreateDetail.builder()
			.productId(1L).quantity(1L).unitPrice(BigDecimal.TEN).build();
		OrderRequestDto.CreateDetail detailDto2 = OrderRequestDto.CreateDetail.builder()
			.productId(2L).quantity(3L).unitPrice(BigDecimal.TWO).build();
		details.add(detailDto1);
		details.add(detailDto2);

		OrderRequestDto.Create createDto = OrderRequestDto.Create.builder()
			.userAuthId("Test Auth").addressId(1L).totalAmount(new BigDecimal(10000)).details(details).build();

		Order order = Order.builder().id(1L).userId(1L).build();
		when(orderRepository.save(any(Order.class))).thenReturn(order);
		orderService.createOrder(createDto, 1L);
		verify(orderRepository).save(any(Order.class));
	}

}