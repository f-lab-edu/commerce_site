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
		//TODO 상품의 수량이 없어서 또는 상태 변경으로 구매하지 못할 경우에 대한 검증이 필요함
		Order order = orderService.createOrder(dto);
		orderDetailService.createOrderDetails(dto.getDetails(), order);
		List<OrderDetailResponseDto.Get> orderDetails = orderDetailService.getOrderDetails(order.getId());
		Address address = addressService.getAddress(dto.getAddressId(), user);
		shipmentService.createShipment(order, orderDetails, address);
	}
}
