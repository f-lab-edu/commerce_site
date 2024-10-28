package org.example.commerce_site.application.order;

import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.application.order.dto.OrderResponseDto;
import org.example.commerce_site.attribute.OrderStatus;
import org.example.commerce_site.common.domain.IdKeyEntity;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.infrastructure.order.CustomOrderRepository;
import org.example.commerce_site.infrastructure.order.OrderRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;
	private final CustomOrderRepository customOrderRepository;

	@Transactional
	public Order createOrder(OrderRequestDto.Create dto, Long userId) {
		return orderRepository.save(OrderRequestDto.Create.toEntity(dto, userId));
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
	public <T extends IdKeyEntity> Page<OrderResponseDto.Get> getOrderList(PageRequest pageRequest, String keyword, T user) {
		return customOrderRepository.getOrders(pageRequest, keyword, user);
	}
}
