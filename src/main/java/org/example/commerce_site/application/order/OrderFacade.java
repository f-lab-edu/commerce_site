package org.example.commerce_site.application.order;

import java.util.List;

import org.example.commerce_site.application.address.AddressService;
import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.application.shipment.ShipmentService;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderFacade {
	private final UserService userService;
	private final OrderService orderService;
	private final OrderDetailService orderDetailService;
	private final ShipmentService shipmentService;
	private final AddressService addressService;

	@Transactional
	public void create(OrderRequestDto.Create dto) {
		User user = userService.getUser(dto.getUserId());
		Order order = orderService.createOrder(dto);
		List<OrderDetailResponseDto.Get> orderDetails = orderDetailService.createOrderDetails(dto.getDetails(), order);
		Address address = addressService.getAddress(dto.getAddressId(), user);
		shipmentService.createShipment(order, orderDetails, address);
	}
}
