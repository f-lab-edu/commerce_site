package org.example.commerce_site.application.payment;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.example.commerce_site.domain.Payment;
import org.example.commerce_site.infrastructure.payment.PaymentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class PaymentServiceTest {
	@InjectMocks
	private PaymentService paymentService;

	@Mock
	private PaymentRepository paymentRepository;

	@Test
	void create_ShouldSavePayment() {
		Payment payment = new Payment();
		when(paymentRepository.save(any(Payment.class))).thenReturn(payment);

		paymentService.create(payment);

		verify(paymentRepository).save(payment);
	}
}