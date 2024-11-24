package org.example.commerce_site.application.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.attribute.ShipmentStatus;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.order.CustomOrderRepository;
import org.example.commerce_site.infrastructure.order.OrderRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {
	@InjectMocks
	private OrderService orderService;

	@Mock
	private OrderRepository orderRepository;

	@Mock
	private CustomOrderRepository customOrderRepository;

	private Long orderId = 1L;
	private Long userId = 1L;
	private Order order = Order.builder().id(orderId).userId(userId).build();

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

	@Test
	public void create_ShouldReturnOrder_WhenOrderExists() {
		when(orderRepository.findByIdAndUserId(any(Long.class), any(Long.class))).thenReturn(Optional.of(order));

		Order result = orderService.getOrder(orderId, userId);

		verify(orderRepository).findByIdAndUserId(any(Long.class), any(Long.class));
		assertNotNull(result);
		assertEquals(orderId, result.getId());
	}

	@Test
	public void create_ShouldThrowCustomException_WhenOrderNotFound() {
		when(orderRepository.findByIdAndUserId(any(Long.class), any(Long.class))).thenReturn(Optional.empty());

		CustomException exception = assertThrows(CustomException.class, () -> {
			orderService.getOrder(orderId, userId);
		});
		assertEquals(ErrorCode.ORDER_NOT_FOUND, exception.getErrorCode());
	}

	@Test
	public void updateStatus_ShouldUpdateOrderStatus() {
		OrderStatus newStatus = OrderStatus.SHIPPED;
		when(orderRepository.save(any(Order.class))).thenReturn(order);

		orderService.updateStatus(order, newStatus);

		verify(orderRepository).save(any(Order.class));
		assertEquals(newStatus, order.getStatus());
	}

	@Test
	public void getOrderList_ShouldReturnOrderList_WhenKeywordIsValid() {
		OrderDetailResponseDto.GetList orderDetailDto = new OrderDetailResponseDto.GetList(
			1L,
			LocalDateTime.now(),
			1001L,
			3L,
			2001L,
			BigDecimal.valueOf(199.99),
			"Product Name Example",
			ShipmentStatus.PENDING,
			LocalDateTime.now().minusDays(1),
			LocalDateTime.now().minusHours(5),
			"010-1234-5678",
			"12345",
			"123 Main Street",
			"123-456",
			"Apt 101"
			);
		OrderResponseDto.Get orderResponseDto = new OrderResponseDto.Get(1L, BigDecimal.TEN, OrderStatus.DELIVERED, List.of(orderDetailDto));
		List<OrderResponseDto.Get> orderList = List.of(orderResponseDto);
		Page<OrderResponseDto.Get> page = new PageImpl<>(orderList);

		when(customOrderRepository.getOrders(any(PageRequest.class), any(String.class), any())).thenReturn(page);

		Page<OrderResponseDto.Get> result = orderService.getOrderList(PageRequest.of(1, 10), "keyword", User.builder().id(1L).build());

		verify(customOrderRepository).getOrders(any(PageRequest.class), any(String.class), any());
		assertEquals(1, result.getTotalElements());
	}

}