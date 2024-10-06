package org.example.commerce_site.application.payment;

import org.example.commerce_site.domain.Payment;
import org.example.commerce_site.infrastructure.payment.PaymentRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaymentService {
	private final PaymentRepository paymentRepository;

	@Transactional
	public Payment create(Payment order) {
		return paymentRepository.save(order);
	}
}
