package org.example.commerce_site.application.order;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;

import org.example.commerce_site.application.address.AddressService;
import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.shipment.ShipmentService;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.attribute.UserRoles;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@ExtendWith(MockitoExtension.class)
class OrderFacadeTest {
	@InjectMocks
	private OrderFacade orderFacade;

	@Mock
	private UserService userService;

	@Mock
	private OrderService orderService;

	@Mock
	private OrderDetailService orderDetailService;

	@Mock
	private ShipmentService shipmentService;

	@Mock
	private AddressService addressService;

	@Mock
	private ProductService productService;

	@Test
	public void create_ShouldCreateOrder() {
		Long userId = 1L;
		Long addressId = 1L;
		OrderRequestDto.Create dto = OrderRequestDto.Create.builder()
			.userAuthId("Test Auth")
			.addressId(addressId)
			.details(Collections.emptyList())
			.build();

		User user = User.builder().id(userId).build();
		Order order = Order.builder().id(1L).userId(userId).build();
		Address address = Address.builder().id(addressId).build();
		List<OrderDetailResponseDto.Get> orderDetails = Collections.emptyList();

		when(userService.getUser(dto.getUserAuthId())).thenReturn(user);
		when(orderService.createOrder(eq(dto), eq(userId))).thenReturn(order);
		when(addressService.getAddress(addressId, user)).thenReturn(address);
		when(orderDetailService.getOrderDetails(order.getId())).thenReturn(orderDetails);

		orderFacade.create(dto);

		verify(userService).getUser(dto.getUserAuthId());
		verify(productService).decreaseStockOnPurchase(dto.getDetails());
		verify(orderService).createOrder(eq(dto), eq(userId)); // 매처 사용
		verify(orderDetailService).createOrderDetails(dto.getDetails(), order);
		verify(orderDetailService).getOrderDetails(order.getId());
		verify(addressService).getAddress(addressId, user);
		verify(shipmentService).createShipment(order, orderDetails, address);
	}

	@Test
	void cancel_ShouldCancelOrder_WhenOrderCanBeCancelled() {
		String userAuthId = "user123";
		Long orderId = 1L;

		User user = User.builder().id(1L).authId(userAuthId).build();
		Order order = Order.builder().id(orderId).status(OrderStatus.PENDING).userId(user.getId()).build();
		List<OrderDetailResponseDto.Get> orderDetails = List.of(
			OrderDetailResponseDto.Get.builder()
				.orderId(1L)
				.productId(1L)
				.quantity(2L)
				.unitPrice(new BigDecimal(100))
				.build()
		);

		when(userService.getUser(userAuthId)).thenReturn(user);
		when(orderService.getOrder(orderId, user.getId())).thenReturn(order);
		when(orderDetailService.getOrderDetails(orderId)).thenReturn(orderDetails);

		orderFacade.cancel(userAuthId, orderId);

		verify(orderService).updateStatus(order, OrderStatus.CANCELLED);
		verify(productService).restoreStockOnCancel(orderDetails);
	}

	@Test
	void cancel_ShouldThrowException_WhenOrderCannotBeCancelled() {
		String userAuthId = "user123";
		Long orderId = 1L;

		User user = User.builder().id(1L).authId(userAuthId).build();
		Order order = Order.builder().id(orderId).status(OrderStatus.SHIPPED).userId(user.getId()).build();

		when(userService.getUser(userAuthId)).thenReturn(user);
		when(orderService.getOrder(orderId, user.getId())).thenReturn(order);

		CustomException exception = assertThrows(CustomException.class, () -> orderFacade.cancel(userAuthId, orderId));
		assertEquals(ErrorCode.ORDER_ALREADY_SHIPPED, exception.getErrorCode());
	}

	@Test
	void getOrderList_ShouldReturnUserPagedOrders() {
		String userAuthId = "user123";
		int page = 1;
		int size = 10;
		String keyword = "keyword";

		User user = User.builder().id(1L).authId(userAuthId).build();
		PageRequest pageRequest = PageRequest.of(page - 1, size);
		Page<OrderResponseDto.Get> orders = new PageImpl<>(
			List.of(
				new OrderResponseDto.Get(1L, new BigDecimal(1000), OrderStatus.PENDING, null))
		);

		when(userService.getUser(userAuthId)).thenReturn(user);
		when(orderService.getOrderList(pageRequest, keyword, user)).thenReturn(orders);

		Page<OrderResponseDto.Get> result = orderFacade.getOrderList(page, size, keyword, userAuthId, UserRoles.ROLE_USER.name());

		assertEquals(orders, result);
		verify(orderService).getOrderList(pageRequest, keyword, user);
	}
}