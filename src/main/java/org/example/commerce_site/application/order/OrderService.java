package org.example.commerce_site.application.order;

import org.example.commerce_site.application.order.dto.OrderRequestDto;
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
	public Order createOrder(OrderRequestDto.Create dto) {
		return orderRepository.save(OrderRequestDto.Create.toEntity(dto));
	}
}
