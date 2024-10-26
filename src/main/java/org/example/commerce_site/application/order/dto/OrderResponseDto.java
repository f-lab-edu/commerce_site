package org.example.commerce_site.application.order.dto;

import java.math.BigDecimal;
import java.util.List;

import org.example.commerce_site.attribute.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

public class OrderResponseDto {
	@Builder
	@Getter
	@AllArgsConstructor
	public static class Get {
		private Long id;
		private BigDecimal totalAmount;
		private OrderStatus status;
		private List<OrderDetailResponseDto.Get> orderDetails;

		public Get(Long id, BigDecimal totalAmount, OrderStatus status) {
			this.id = id;
			this.totalAmount = totalAmount;
			this.status = status;
		}

		public void setOrderDetails(List<OrderDetailResponseDto.Get> orderDetails) {
			this.orderDetails = orderDetails;
		}
	}
}
