package org.example.commerce_site.application.order;

import java.util.List;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;
import org.example.commerce_site.infrastructure.order.OrderDetailBulkRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
	private final OrderDetailBulkRepository orderDetailBulkRepository;

	@Transactional
	public List<OrderDetailResponseDto.Get> createOrderDetails(List<OrderRequestDto.CreateDetail> details,
		Order order) {
		List<OrderDetail> orderDetails = details.stream()
			.map(dto -> OrderRequestDto.CreateDetail.toEntity(dto, order))
			.toList();
		return orderDetailBulkRepository.saveAll(orderDetails, order.getId());
	}
}
