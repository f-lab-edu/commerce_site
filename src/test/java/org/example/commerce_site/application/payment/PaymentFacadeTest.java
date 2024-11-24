package org.example.commerce_site.application.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.commerce_site.application.order.OrderService;
import org.example.commerce_site.application.payment.dto.PaymentRequestDto;
import org.example.commerce_site.application.user.UserService;
import org.example.commerce_site.domain.Order;
import org.example.commerce_site.domain.Payment;
import org.example.commerce_site.domain.User;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentFacadeTest {
	@InjectMocks
	private PaymentFacade paymentFacade;

	@Mock
	private PaymentService paymentService;

	@Mock
	private UserService userService;

	@Mock
	private OrderService orderService;

	@Test
	void createPayment_ShouldCallServicesAndCreatePayment() {
		String userAuthId = "auth123";
		Long orderId = 1L;

		PaymentRequestDto.Create dto = PaymentRequestDto.Create.builder()
			.userAuthId(userAuthId).orderId(orderId).build();

		User user = User.builder().id(100L).authId(userAuthId).build();

		Order order = Order.builder().id(orderId).userId(user.getId()).build();

		when(userService.getUser(userAuthId)).thenReturn(user);
		when(orderService.getOrder(orderId, user.getId())).thenReturn(order);

		paymentFacade.createPayment(dto);

		verify(userService, times(1)).getUser(userAuthId);
		verify(orderService, times(1)).getOrder(orderId, user.getId());
		verify(paymentService, times(1)).create(any(Payment.class));
	}
}