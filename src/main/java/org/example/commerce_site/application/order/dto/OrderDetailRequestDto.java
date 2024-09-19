package org.example.commerce_site.application.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;

import lombok.Builder;

public class OrderDetailRequestDto {
	@Builder
	public static class Create {
		private LocalDateTime createdAt;
		private Long id;
		private Order order;
		private Long productId;
		private Long quantity;
		private BigDecimal unitPrice;

		public static OrderDetail toEntity(OrderDetailResponseDto.Get orderDetail, Order order) {
			return OrderDetail.builder()
				.id(orderDetail.getId())
				.order(order)
				.productId(orderDetail.getProductId())
				.unitPrice(orderDetail.getUnitPrice())
				.quantity(orderDetail.getQuantity())
				.createdAt(orderDetail.getCreatedAt())
				.build();
		}
	}
}
