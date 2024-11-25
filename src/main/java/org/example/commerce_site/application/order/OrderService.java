package org.example.commerce_site.application.order;

import java.util.List;

import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.common.aop.RedissonLock;
import org.example.commerce_site.common.domain.Account;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.User;
import org.example.commerce_site.infrastructure.order.CustomOrderRepository;
import org.example.commerce_site.infrastructure.order.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final CustomOrderRepository customOrderRepository;

	@Transactional
	public Order createOrder(Order order) {
		return orderRepository.save(order);
	}

	@Transactional(readOnly = true)
	public Order getOrder(Long orderId, Long userId) {
		return orderRepository.findByIdAndUserId(orderId, userId).orElseThrow(
			() -> new CustomException(ErrorCode.ORDER_NOT_FOUND)
		);
	}

	@Transactional
	public void updateStatus(Order order, OrderStatus orderStatus) {
		order.updateOrderStatus(orderStatus);
		orderRepository.save(order);
	}

	@Transactional(readOnly = true)
	public <T extends Account> Page<OrderResponseDto.Get> getOrderList(PageRequest pageRequest, String keyword,
		T user) {
		return customOrderRepository.getOrders(pageRequest, keyword, user);
	}

	@RedissonLock(value = "#user.id + ':' + #productId")
	@Transactional(readOnly = true)
	public boolean verifyOneOffPurchase(User user, Long productId) {
		List<OrderResponseDto.GetOneOff> orderList = customOrderRepository.getOrderListByUserIdAndProductId(user,
			productId);

		if (orderList.isEmpty()) {
			return false;
		} else {
			return true;
		}
	}
}
