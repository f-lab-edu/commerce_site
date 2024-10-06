package org.example.commerce_site.application.payment;

import org.example.commerce_site.application.order.OrderService;
import org.example.commerce_site.application.payment.dto.PaymentRequestDto;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.User;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentFacade {
	private final PaymentService paymentService;
	private final UserService userService;
	private final OrderService orderService;

	public void createPayment(PaymentRequestDto.Create dto) {
		User user = userService.getUser(dto.getUserAuthId());
		Order order = orderService.getOrder(dto.getOrderId(), user.getId());
		paymentService.create(PaymentRequestDto.Create.toEntity(dto, order, user.getId()));
	}
}
