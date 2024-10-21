package org.example.commerce_site.application.order;

import java.util.List;

import org.example.commerce_site.application.order.dto.OrderDetailResponseDto;
import org.example.commerce_site.application.order.dto.OrderRequestDto;
import org.example.commerce_site.common.exception.CustomException;
import org.example.commerce_site.common.exception.ErrorCode;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;
import org.example.commerce_site.infrastructure.order.OrderDetailBulkRepository;
import org.example.commerce_site.infrastructure.order.OrderDetailRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderDetailService {
	private final OrderDetailRepository orderDetailRepository;
	private final OrderDetailBulkRepository orderDetailBulkRepository;

	@Transactional
	public void createOrderDetails(List<OrderRequestDto.CreateDetail> details,
		Order order) {
		List<OrderDetail> orderDetails = details.stream()
			.map(dto -> OrderRequestDto.CreateDetail.toEntity(dto, order))
			.toList();
		orderDetailBulkRepository.saveAll(orderDetails, order.getId());
	}

	@Transactional(readOnly = true)
	public List<OrderDetailResponseDto.Get> getOrderDetails(Long orderId) {
		return OrderDetailResponseDto.Get.toDtoList(orderDetailRepository.findAllByOrderId(orderId));
	}

	@Transactional(readOnly = true)
	public OrderDetail getOrderDetail(Long orderDetailId) {
		return orderDetailRepository.findById(orderDetailId).orElseThrow(
			() -> new CustomException(ErrorCode.ORDER_DETAIL_NOT_FOUND)
		);
	}
}
