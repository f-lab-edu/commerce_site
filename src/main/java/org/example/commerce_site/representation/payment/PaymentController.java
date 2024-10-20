package org.example.commerce_site.representation.payment;

import org.example.commerce_site.application.payment.PaymentFacade;
import org.example.commerce_site.common.auth.UserCheck;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.payment.dto.PaymentRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/payments")
public class PaymentController {
	private final PaymentFacade paymentFacade;

	@UserCheck
	@PostMapping("/{order_id}")
	public ApiSuccessResponse createPayment(
		@PathVariable("order_id") Long orderId,
		@RequestAttribute("userId") String userAuthId,
		@RequestBody PaymentRequest.Create request
	) {
		paymentFacade.createPayment(PaymentRequest.Create.toDto(request, orderId, userAuthId));
		return ApiSuccessResponse.success();
	}
}
