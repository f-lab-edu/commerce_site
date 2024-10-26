package org.example.commerce_site.representation.order;

import org.example.commerce_site.application.order.OrderFacade;
import org.example.commerce_site.common.response.ApiSuccessResponse;
import org.example.commerce_site.representation.order.dto.OrderRequest;
import org.example.commerce_site.representation.order.dto.OrderResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@PreAuthorize("hasAuthority('ROLE_USER')")
@RequestMapping("/orders")
public class OrderController {
	private final OrderFacade orderFacade;

	@PostMapping()
	public ApiSuccessResponse createOrder(
		@RequestAttribute("user_id") String userAuthId,
		@RequestBody OrderRequest.Create request
	) {
		orderFacade.create(OrderRequest.Create.toDto(request, userAuthId));
		return ApiSuccessResponse.success();
	}

	@DeleteMapping("/{order_id}")
	public ApiSuccessResponse cancelOrder(
		@RequestAttribute("user_id") String userAuthId,
		@PathVariable(name = "order_id") Long orderId
	) {
		orderFacade.cancel(userAuthId, orderId);
		return ApiSuccessResponse.success();
	}

	@GetMapping
	public ApiSuccessResponse.PageList<OrderResponse.Get> getOrders(
		@RequestParam(value = "page", defaultValue = "1") int page,
		@RequestParam(value = "size", defaultValue = "10") int size,
		@RequestParam(value = "keyword", required = false) String keyword,
		@RequestAttribute("user_id") String userAuthId
	) {
		return ApiSuccessResponse.success(
			OrderResponse.Get.of(orderFacade.getOrderList(page, size, keyword, userAuthId)));
	}
}
