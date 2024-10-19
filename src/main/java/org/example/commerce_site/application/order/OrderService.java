package org.example.commerce_site.application.order;

import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.infrastructure.order.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
	private final OrderRepository orderRepository;

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
}
