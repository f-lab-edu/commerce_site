package org.example.commerce_site.application.order;

import java.util.List;

import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;
import org.example.commerce_site.infrastructure.order.OrderDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
	private final OrderDetailRepository orderDetailRepository;

	@Transactional
	public List<OrderDetail> createOrderDetails(List<OrderRequestDto.CreateDetail> details, Order order) {
		return details.stream()
			.map(dto -> orderDetailRepository.save(OrderRequestDto.CreateDetail.toEntity(dto, order)))
			.toList();
	}
}
