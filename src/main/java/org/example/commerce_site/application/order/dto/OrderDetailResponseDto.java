package org.example.commerce_site.application.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;

import lombok.Builder;

public class OrderDetailResponseDto {
	@Builder
	public static class Get {
		protected LocalDateTime createdAt;
		private Long id;
		private Long orderId;
		private Long productId;
		private Long quantity;
		private BigDecimal unitPrice;

		public static OrderDetail toEntity(Get orderDetailItem, Order order) {
			return OrderDetail.builder()
				.id(orderDetailItem.id)
				.order(order)
				.productId(orderDetailItem.productId)
				.unitPrice(orderDetailItem.unitPrice)
				.quantity(orderDetailItem.quantity)
				.createdAt(orderDetailItem.createdAt)
				.build();
		}
	}
}
