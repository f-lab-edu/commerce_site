package org.example.commerce_site.application.order;

import java.util.List;

import org.example.commerce_site.application.address.AddressService;
import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.shipment.ShipmentService;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
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
	private final ProductService productService;

	@Transactional
	public void create(OrderRequestDto.Create dto) {
		User user = userService.getUser(dto.getUserAuthId());
		productService.decreaseStockOnPurchase(dto.getDetails());
		Order order = orderService.createOrder(dto, user.getId());
		orderDetailService.createOrderDetails(dto.getDetails(), order);
		List<OrderDetailResponseDto.Get> orderDetails = orderDetailService.getOrderDetails(order.getId());
		Address address = addressService.getAddress(dto.getAddressId(), user);
		shipmentService.createShipment(order, orderDetails, address);
	}

	@Transactional
	public void cancel(String userAuthId, Long orderId) {
		User user = userService.getUser(userAuthId);
		Order order = orderService.getOrder(orderId, user.getId());
		if (!OrderStatus.isPhaseCanCancelOrder(order.getStatus())){
			throw new CustomException(ErrorCode.ORDER_ALREADY_SHIPPED);
		}
		orderService.cancelOrder(order);
		List<OrderDetailResponseDto.Get> orderDetails = orderDetailService.getOrderDetails(order.getId());
		productService.restoreStockOnCancel(orderDetails);
	}
}
