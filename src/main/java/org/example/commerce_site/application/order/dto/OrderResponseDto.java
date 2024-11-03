package org.example.commerce_site.application.order.dto;

import java.math.BigDecimal;
import java.util.List;

import org.example.commerce_site.attribute.OrderStatus;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class OrderResponseDto {
	@Getter
	@AllArgsConstructor
	public static class Get {
		private Long id;
		private BigDecimal totalAmount;
		private OrderStatus status;
		private List<OrderDetailResponseDto.GetList> orderDetails;
	}
}
