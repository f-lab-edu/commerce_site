package org.example.commerce_site.application.payment.dto;

import java.math.BigDecimal;

import org.example.commerce_site.attribute.PaymentMethod;
import org.example.commerce_site.attribute.PaymentStatus;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.Payment;

import lombok.Builder;
import lombok.Getter;

public class PaymentRequestDto {
	@Builder
	@Getter
	public static class Create {
		public PaymentMethod paymentMethod;
		public BigDecimal amount;
		public String userAuthId;
		public Long orderId;

		public static Payment toEntity(Create dto, Order order, Long userId) {
			return Payment.builder()
				.amount(dto.getAmount())
				.status(PaymentStatus.PENDING)
				.paymentMethod(dto.getPaymentMethod())
				.userId(userId)
				.order(order)
				.build();
		}
	}
}
