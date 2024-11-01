package org.example.commerce_site.application.order;

import java.util.List;

import org.example.commerce_site.application.address.AddressService;
import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.application.partner.PartnerService;
import org.example.commerce_site.application.product.ProductService;
import org.example.commerce_site.application.shipment.ShipmentService;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.common.domain.Account;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Address;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderFacade {
	private final PartnerService partnerService;
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
		if (!OrderStatus.isPhaseCanCancelOrder(order.getStatus())) {
			throw new CustomException(ErrorCode.ORDER_ALREADY_SHIPPED);
		}
		orderService.updateStatus(order, OrderStatus.CANCELLED);
		List<OrderDetailResponseDto.Get> orderDetails = orderDetailService.getOrderDetails(order.getId());
		productService.restoreStockOnCancel(orderDetails);
	}

	public Page<OrderResponseDto.Get> getOrderList(int page, int size, String keyword, String userAuthId,
		String authority) {
		Account user = null;
		if (authority.equals("ROLE_USER")) {
			user = userService.getUser(userAuthId);
		} else if (authority.equals("ROLE_PARTNER")) {
			user = partnerService.getPartner(userAuthId);
		}
		return orderService.getOrderList(PageRequest.of(page - 1, size), keyword, user);
	}

}
