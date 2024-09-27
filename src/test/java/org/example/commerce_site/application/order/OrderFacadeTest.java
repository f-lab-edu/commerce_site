package org.example.commerce_site.application.order;

import static org.mockito.Mockito.*;

import java.util.Collections;
import java.util.List;

import org.example.commerce_site.application.address.AddressService;
import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.application.shipment.ShipmentService;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

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

	@Test
	public void testCreate_OrderSuccessfullyCreated() {
		Long userId = 1L;
		Long addressId = 1L;
		OrderRequestDto.Create dto = OrderRequestDto.Create.builder()
			.userId(userId)
			.addressId(addressId)
			.details(Collections.emptyList())
			.build();

		User user = User.builder().id(userId).build();
		Order order = Order.builder().id(1L).userId(userId).build();
		Address address = Address.builder().id(addressId).build();
		List<OrderDetailResponseDto.Get> orderDetails = Collections.emptyList();

		when(userService.getUser(userId)).thenReturn(user);
		when(orderService.createOrder(dto)).thenReturn(order);
		when(orderDetailService.createOrderDetails(dto.getDetails(), order)).thenReturn(orderDetails);
		when(addressService.getAddress(addressId, user)).thenReturn(address);

		orderFacade.create(dto);

		verify(userService).getUser(userId);
		verify(orderService).createOrder(dto);
		verify(orderDetailService).createOrderDetails(dto.getDetails(), order);
		verify(addressService).getAddress(addressId, user);
		verify(shipmentService).createShipment(order, orderDetails, address);
	}
}