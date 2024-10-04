package org.example.commerce_site.application.order.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.OrderDetail;

import lombok.Builder;
import lombok.Getter;

public class OrderDetailResponseDto {
	@Getter
	@Builder
	public static class Get {
		private LocalDateTime createdAt;
		private Long id;
		private Long productId;
		private Long quantity;
		private Long orderId;
		private BigDecimal unitPrice;

		public static Get toDto(OrderDetail orderDetail) {
			return Get.builder()
				.createdAt(orderDetail.getCreatedAt())
				.id(orderDetail.getId())
				.orderId(orderDetail.getOrder().getId())
				.productId(orderDetail.getProductId())
				.quantity(orderDetail.getQuantity())
				.unitPrice(orderDetail.getUnitPrice())
				.build();
		}

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

		public static List<OrderDetailResponseDto.Get> toDtoList(List<OrderDetail> orderDetails) {
			return orderDetails.stream().map(Get::toDto).toList();
		}
	}
}
