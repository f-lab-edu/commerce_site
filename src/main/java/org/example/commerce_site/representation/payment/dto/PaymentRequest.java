package org.example.commerce_site.representation.payment.dto;

import java.math.BigDecimal;

import org.example.commerce_site.application.payment.dto.PaymentRequestDto;
import org.example.commerce_site.attribute.PaymentMethod;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

public class PaymentRequest {
	@Getter
	public static class Create {
		@NotNull(message = "payment method can not NULL")
		public PaymentMethod paymentMethod;

		@NotNull(message = "amount can not NULL")
		public BigDecimal amount;

		public static PaymentRequestDto.Create toDto(PaymentRequest.Create create, Long orderId, String userAuthId) {
			return PaymentRequestDto.Create.builder()
				.paymentMethod(create.paymentMethod)
				.amount(create.amount)
				.orderId(orderId)
				.userAuthId(userAuthId)
				.build();
		}
	}
}
