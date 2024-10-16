package org.example.commerce_site.representation.order;

import org.example.commerce_site.application.order.OrderFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.order.request.OrderRequest;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {
	private final OrderFacade orderFacade;

	@PostMapping()
	public ApiSuccessResponse createOrder(
		@RequestAttribute("userId") String userAuthId,
		@RequestBody OrderRequest.Create request
	) {
		orderFacade.create(OrderRequest.Create.toDto(request, userAuthId));
		return ApiSuccessResponse.success();
	}
}
